package com.oracle.frontweb.httpclient.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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

			response = httpClient.execute(httpGet);

			httpGet.setConfig(requestConfig);

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
			System.out.println(response.getStatusLine().getStatusCode());
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
	public HttpClientReturn doPut(String url) throws  Exception{
		CloseableHttpResponse response = null;
		int code;
		try {
			HttpPut httpPut = new HttpPut(url);
			httpPut.setConfig(requestConfig);
			response = httpClient.execute(httpPut);
			code = response.getStatusLine().getStatusCode();
			if (code==204) {
				String body = EntityUtils.toString(response.getEntity(), "utf-8");
				return new HttpClientReturn(204, body);
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return new HttpClientReturn(code, null);
	}
	
	/*
	 * 带参数的put请求
	 */
	public HttpClientReturn doPut(String url,Map<String, Object> map) throws Exception{
		int code;
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> parameters = new ArrayList<>();
			if (map!=null) {
				for (Entry<String, Object> entry : map.entrySet()) {
					parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "utf-8");
			
			HttpPut httpPut = new HttpPut(url);
			httpPut.setConfig(requestConfig);
			httpPut.setEntity(formEntity);
			response = httpClient.execute(httpPut);
			code = response.getStatusLine().getStatusCode();
			if (code==204) {
				String body = EntityUtils.toString(response.getEntity(), "utf-8");
				return new HttpClientReturn(204, body);
			}
		} finally{
			if (response != null) {
				response.close();
			}
		}
		
		return new HttpClientReturn(code, null);
	}
	
	/*
	 * 不带参数的delete请求
	 * 
	 */
	public HttpClientReturn doDelete(String url) throws  Exception{
		int code;
		CloseableHttpResponse response = null;
		try {
			HttpDelete httpDelete = new HttpDelete(url);
			httpDelete.setConfig(requestConfig);
			
			response = httpClient.execute(httpDelete);
			code = response.getStatusLine().getStatusCode();
			if (code==204) {
				String body = EntityUtils.toString(response.getEntity(), "utf-8");
				return new HttpClientReturn(204, body);
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return new HttpClientReturn(code, null);
	}
	
	/*
	 * 带参数的delete请求
	 */
	public HttpClientReturn doDelete(String url,Map<String, Object> map) throws Exception{
		URIBuilder uriBuilder = new URIBuilder(url);
		for (Entry<String, Object> entry : map.entrySet()) {
			uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
		}
		URI uri = uriBuilder.build();
		return doDelete(uri.toString());
	}
	
	/*
	 * 
	 * 发送json数据
	 */
	public HttpClientReturn daPostJson(String url,String json) throws Exception{
		int code;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			if (!StringUtils.isEmpty(json)) {
				StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
				entity.setContentEncoding("utf-8");
				httpPost.setEntity(entity);
			}
			
			httpPost.setConfig(requestConfig);
			response = httpClient.execute(httpPost);
			
			code = response.getStatusLine().getStatusCode();
			if (code==200) {
				String jsonData = EntityUtils.toString(response.getEntity(), "utf-8");
				return new HttpClientReturn(200, jsonData);
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return new HttpClientReturn(code, null);
	}
	
	/*
	 * 通过post发delete请求
	 * 
	 */
	public  HttpClientReturn doDeleteByPost(String url,Map<String, Object> map) throws Exception{
		map.put("_method", "DELETE");
		return doPost(url, map);
		
	}
	
	/**
	 * 
	 * @param i
	 * @param n
	 */
	public void hh(String i,String n){
		
	}
	
}
