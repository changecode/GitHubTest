package com.java.utils.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.google.gson.Gson;

/**
 * redis工具类
 * 
 * @author Tim
 *
 */
public class RedisCacher {

	private static int DEFAULT_DB_INDEX = 0;
	private static int DB_INDEX_1 = 1;
	private static JedisPool jedisPool = null;

	private String host;
	private int port;
	private String password;

	private static Gson gson = new Gson();

	private static final String ClassName = "CN";
	private static final String ObjectKey = "OBJK";
	private static final String EXPIRE_SECONDS = "expireSeconds";
	private static final String TIMESTAMP = "timestamp";

	public RedisCacher(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public RedisCacher(String host, int port, String password) {
		this.host = host;
		this.port = port;
		this.password = password;
	}

	/**
	 * 初始化redis连接池
	 */
	public void init() {
		try {
			if (jedisPool == null) {

				// 配置如下的4个参数就够了。
				JedisPoolConfig config = new JedisPoolConfig();
				// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
				// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
				config.setMaxTotal(100);
				// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
				config.setMaxIdle(10);
				// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
				config.setMaxWaitMillis(10000L);
				// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
				config.setTestOnBorrow(true);
				jedisPool = new JedisPool(config, host, port, 10000, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得redis实例
	 */
	public Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				resource.select(DEFAULT_DB_INDEX);
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	private String objectToJSONString(Object val, Integer seconds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ObjectKey, gson.toJson(val));
		map.put(ClassName, val.getClass().getName());
		map.put(EXPIRE_SECONDS, seconds);
		map.put(TIMESTAMP, new Date().getTime());
		return gson.toJson(map);
	}

	@SuppressWarnings("unchecked")
	private Object jsonStringToObject(String value) throws Exception {
		Map<String, Object> map = gson.fromJson(value, new HashMap<String, Object>().getClass());
		Object obj = map.get(ObjectKey);
		if (obj == null) {
			return null;
		}
		Integer seconds = ((Double) map.get(EXPIRE_SECONDS)).intValue();
		if (seconds != null) {
			Long timestamp = ((Double) map.get(TIMESTAMP)).longValue();
			Long now = new Date().getTime();
			if ((timestamp + (seconds.longValue() * 1000)) < now) {
				// 过期
				throw new Exception("the value has expire,but not expire in redis...");
			}
		}

		String objStr = (String) obj;
		String className = (String) map.get(ClassName);
		return gson.fromJson(objStr, Class.forName(className));
	}

	// 删除
	public void delete(String... keys) {
		Jedis jedis = getJedis();
		try {
			if (jedis != null) {
				if (keys != null) {
					jedis.del(keys);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 值设置到redis中
	 * 
	 * @param key
	 * @param val
	 * @param seconds
	 *            单位秒
	 */
	public void set(String key, Object val, Integer seconds) {
		Jedis jedis = getJedis();
		try {
			if (jedis != null) {
				jedis.set(key, objectToJSONString(val, seconds));
				if (seconds != null) {
					jedis.expire(key, seconds);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 新增将hash值设置到redis中
	 * 
	 * @param key
	 * @param val
	 * @param seconds
	 *            单位秒
	 */
	public void hset(String key, String field, String val, Integer seconds) {
		Jedis jedis = getJedis();
		try {
			if (jedis != null) {
				jedis.select(DB_INDEX_1);
				jedis.hset(key, field, val);
				if (seconds != null) {
					jedis.expire(key, seconds);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		Jedis jedis = getJedis();
		try {
			if (jedis != null) {
				String str = jedis.get(key);
				if (StringUtils.isEmpty(str)) {
					return null;
				}
				return jsonStringToObject(str);
			}
			return null;
		} catch (Exception e) {
			if (jedis.exists(key)) {
				delete(key);
			}
			return null;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 根据key field 获取hash值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		Jedis jedis = getJedis();
		try {
			if (jedis != null) {
				jedis.select(DB_INDEX_1);
				String str = jedis.hget(key, field);
				if (StringUtils.isEmpty(str)) {
					return null;
				}
				return str;
			}
			return null;
		} catch (Exception e) {
			if (jedis.exists(key)) {
				delete(key);
			}
			return null;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取hash值
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = getJedis();
		Map<String, String> str = new HashMap<String, String>();
		try {
			if (jedis != null) {
				jedis.select(DB_INDEX_1);
				str = jedis.hgetAll(key);
				if (StringUtils.isEmpty(str.toString())) {
					return null;
				}
				return str;
			}
			return str;
		} catch (Exception e) {
			if (jedis.exists(key)) {
				delete(key);
			}
			return null;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取key模糊查询得到的数组
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> keys(String key) {
		Jedis jedis = getJedis();
		Set<String> str = new HashSet<String>();
		try {
			if (jedis != null) {
				jedis.select(DB_INDEX_1);
				str = jedis.keys(key);
				if (null == str || str.size() == 0) {
					return null;
				}
				return str;
			}
			return str;
		} catch (Exception e) {
			if (jedis.exists(key)) {
				delete(key);
			}
			return null;
		} finally {
			returnResource(jedis);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
