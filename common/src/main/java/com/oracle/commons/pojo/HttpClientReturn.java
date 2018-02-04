package com.oracle.commons.pojo;

public class HttpClientReturn {
	private Integer statusCode;
	private String responseBody;
	public HttpClientReturn() {
		super();
	}
	public HttpClientReturn(Integer statusCode, String responseBody) {
		super();
		this.statusCode = statusCode;
		this.responseBody = responseBody;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	@Override
	public String toString() {
		return "HttpClientReturn [statusCode=" + statusCode + ", responseBody=" + responseBody + "]";
	}
	
}	
