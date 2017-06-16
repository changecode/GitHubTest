package com.java.utils.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求工具支持https
 * @author Tim
 *
 */
public class HttpOrHttpsClientUtils {

	/**
	 * 日志工具
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpOrHttpsClientUtils.class);
	/**
	 * 5分钟
	 */
	public static final int MINUTE_FIVE = 300000;

	/**
	 * 10分钟
	 */
	public static final int MINUTE_TEN = 600000;

	/**
	 * HttpClient
	 */
	private static final HttpClient client = getInstance();

	/**
	 * 让Httpclient支持https
	 * 
	 * @return HttpClient
	 */
	@SuppressWarnings("deprecation")
	private static HttpClient getInstance() {
		X509TrustManager x509mgr = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}

			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
			sslContext.init(null, new TrustManager[] { x509mgr }, null);
		} catch (Exception e) {
			logger.error("error to init httpclient", e);
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(400);// 客户端总并行链接最大数
		connManager.setDefaultMaxPerRoute(40); // 每个主机的最大并行链接数

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setConnectionManager(connManager);
		httpClientBuilder.setSSLSocketFactory(sslsf);
		return httpClientBuilder.build();
	}

	public static final RequestConfig getDefaultTimeOutConfig() {
		return getTimeOutConfig(60000, 30000);
	}

	private static final RequestConfig getTimeOutConfig(int socketTimeout, int connectionTimeout) {
		return RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).build();
	}

	/**
	 * Get方法查询
	 */
	public static String getMethodGetResponse(String address) throws Exception {
		return getMethodGetResponse(address, getDefaultTimeOutConfig());
	}

	/**
	 * Post方法查询
	 */
	public static String getMethodPostResponse(String address, HttpEntity paramEntity) throws Exception {
		RequestConfig config = getDefaultTimeOutConfig();
		return getMethodPostResponse(address, paramEntity, config);
	}

	/**
	 * 自定义超时的Get方法查询
	 */
	public static String getMethodGetResponse(String address, int connectionTimeout, int socketTimeout)
			throws Exception {
		return getMethodGetResponse(address, getTimeOutConfig(socketTimeout, connectionTimeout));
	}

	/**
	 * 自定义超时的Post方法
	 */
	public static String getMethodPostResponse(String address, HttpEntity paramEntity, int connectionTimeout,
			int socketTimeout) throws Exception {
		RequestConfig config = getTimeOutConfig(socketTimeout, connectionTimeout);
		return getMethodPostResponse(address, paramEntity, config);
	}

	/**
	 * Post Entity
	 */
	public static byte[] getMethodPostBytes(String address, HttpEntity paramEntity) throws Exception {
		return getMethodPostContent(address, paramEntity, getDefaultTimeOutConfig());
	}

	/**
	 * HttpClient get方法请求返回Entity
	 */
	public static byte[] getMethodGetContent(String address) throws Exception {
		return getMethodGetContent(address, getDefaultTimeOutConfig());
	}

	/**
	 * HttpClient Get方法请求数据
	 */
	private static String getMethodGetResponse(String address, RequestConfig config) throws Exception {
		logger.info("Start Access Address(" + address + ") With Get Request");
		byte[] result = getMethodGetContent(address, config);
		return new String(result, "utf-8");
	}

	/**
	 * HttpClient Post方法请求数据
	 */
	private static String getMethodPostResponse(String address, HttpEntity paramEntity, RequestConfig config)
			throws Exception {
		logger.info("Begin Access Url(" + address + ") By Post");
		byte[] content = getMethodPostContent(address, paramEntity, config);
		String result = new String(content, "utf-8");
		logger.info("Response -> " + result);
		return result;

	}

	/**
	 * HttpClient get方法请求返回Entity
	 */
	private static byte[] getMethodGetContent(String address, RequestConfig config) throws Exception {
		HttpGet get = new HttpGet(address);
		try {
			logger.info("Start Access Address(" + address + ") With Get Request");
			get.setConfig(config);
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				int code = response.getStatusLine().getStatusCode();
				throw new RuntimeException("HttpGet Access Fail , Return Code(" + code + ")");
			}
			response.getEntity().getContent();
			return convertEntityToBytes(response.getEntity());
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
	}

	/**
	 * Post Entity
	 */
	private static byte[] getMethodPostContent(String address, HttpEntity paramEntity, RequestConfig config)
			throws Exception {
		HttpPost post = new HttpPost(address);
		try {
			if (paramEntity != null) {
				post.setEntity(paramEntity);
			}
			post.setConfig(config);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				int code = response.getStatusLine().getStatusCode();
				throw new RuntimeException("HttpPost Request Access Fail Return Code(" + code + ")");
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				throw new RuntimeException("HttpPost Request Access Fail Response Entity Is null");
			}
			return convertEntityToBytes(entity);
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
	}

	/**
	 * 转化返回为byte数组
	 * 
	 * @param entity
	 * @return byte[]
	 * @throws Exception
	 */
	private static byte[] convertEntityToBytes(HttpEntity entity) throws Exception {
		InputStream inputStream = null;
		try {
			if (entity == null || entity.getContent() == null) {
				throw new RuntimeException("Response Entity Is null");
			}
			inputStream = entity.getContent();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			return out.toByteArray();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	/**
	 * 甜橙小贷发送消息工具
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String path, String params) throws Exception {
		if (path.startsWith("https")) {
			return https(path, params);
		} else {
			return http(path, params);
		}
	}

	public static String http(String path, String params) throws Exception {
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			URL url = new URL(path);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			// 发送post请求参数
			out = new PrintWriter(httpConn.getOutputStream());
			out.println(params);
			out.flush();

			// 读取响应
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				return content.toString();
			} else {
				throw new Exception("请求出现了问题!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.close();
			httpConn.disconnect();
		}
		return null;
	}

	public static String https(String path, String params) throws Exception {
		String res = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(path);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
		request.setConfig(requestConfig);
		StringEntity myEntity = new StringEntity(params, "UTF-8");
		try {
			request.setEntity(myEntity);
			CloseableHttpResponse response = client.execute(request);
			res = EntityUtils.toString(response.getEntity());
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
}
