package com.redis.distributed;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class AccessSpeedLimit {

	private static final Logger log = LoggerFactory.getLogger(AccessSpeedLimit.class);

	public AccessSpeedLimit() {}

	private JedisPool jedisPool;

	public AccessSpeedLimit(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	/**
	 * 
	 * @param key
	 *            resource-key
	 * @param seconds
	 *            每seconds秒最多访问limitCount次
	 * @param limitCount
	 *            超过maxCount次返回false
	 * @return
	 */
	public boolean tryAccess(String key, int seconds, int limitCount) {
		LimitRule limitRule = new LimitRule();
		limitRule.setLimitCount(limitCount);
		limitRule.setSeconds(seconds);
		return tryAccess(key, limitRule);
	}

	/**
	 * 针对资源key,每limitRule.seconds秒最多访问limitRule.limitCount,超过limitCount次返回false
	 * 超过lockCount 锁定lockTime
	 * 
	 * @param key
	 * @param limitRule
	 * @return
	 */
	public boolean tryAccess(String key, LimitRule limitRule) {

		String newKey = "Limit:" + key;
		Jedis jedis = null;
		boolean broken = false;
		long count = -1;

		try {
			jedis = jedisPool.getResource();
			List<String> keys = new ArrayList<String>();
			keys.add(key);

			List<String> args = new ArrayList<String>();
			args.add(Math.max(limitRule.getLimitCount(), limitRule.getLockCount()) + "");
			args.add(limitRule.getSeconds() + "");
			args.add(limitRule.getLockCount() + "");
			args.add(limitRule.getLockTime() + "");
			count = Long.parseLong(jedis.eval(buildLuaScript(limitRule), keys, args) + "");

			return count <= limitRule.getLimitCount();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	private String buildLuaScript(LimitRule limitRule) {
		StringBuilder lua = new StringBuilder();
		lua.append("\nlocal c");
		lua.append("\nc = redis.call('get',KEYS[1])");
		lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
		lua.append("\nreturn c;");
		lua.append("\nend");
		lua.append("\nc = redis.call('incr',KEYS[1])");
		lua.append("\nif tonumber(c) == 1 then");
		lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
		lua.append("\nend");
		if (limitRule.enableLimitLock()) {
			lua.append("\nif tonumber(c) > tonumber(ARGV[3]) then");
			lua.append("\nredis.call('expire',KEYS[1],ARGV[4])");
			lua.append("\nend");
		}
		lua.append("\nreturn c;");
		return lua.toString();
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
