package com.oracle.product.httpclient.utils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.commons.pojo.HttpClientReturn;

@Service
public class HttpClientUtils {

	@Autowired(required = false)
	private CloseableHttpClient httpClient;

	@Autowired(required = false)
	private RequestConfig requestConfig;

	/*
	 * 不带参数的get请求
	 */
	public HttpClientReturn doGet(String url) throws Exception {
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);

			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);


			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				//用EntityUtils将响应实体转成string
				HttpClientReturn httpClientReturn = new HttpClientReturn(response.getStatusLine().getStatusCode(),
						EntityUtils.toString(entity, "utf-8"));
				return httpClientReturn;
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return new HttpClientReturn(response.getStatusLine().getStatusCode(),
				null);
	}

	/*
	 * 带参数的get请求
	 */
	public HttpClientReturn doGet(String url, Map<String, Object> map) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);
		for (Entry<String, Object> entry : map.entrySet()) {
			uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
		}
		URI uri = uriBuilder.build();
		return doGet(uri.toString());
	}

	/*
	 * 不带参数的post请求
	 */
	public HttpClientReturn doPost(String url) throws Exception {
		return doPost(url, null);
	}

	/*
	 * 带参数的post请求
	 * 
	 */
	public HttpClientReturn doPost(String url, Map<String, Object> map) throws Exception {
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> list = new ArrayList<>();
			if (map != null) {
				for (Entry<String, Object> entry : map.entrySet()) {
					list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "utf-8");
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(formEntity);
			httpPost.setConfig(requestConfig);
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				//用EntityUtils将响应实体转成string
				HttpClientReturn httpClientReturn = new HttpClientReturn(response.getStatusLine().getStatusCode(),
						EntityUtils.toString(response.getEntity(), "utf-8"));
				return httpClientReturn;
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return new HttpClientReturn(response.getStatusLine().getStatusCode(),
				null);

	}

	/*
	 * 不带参数的put请求
	 */
}
