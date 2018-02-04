package com.oracle.commons.pojo;

import java.util.List;


public class DataTablesJSONReturn {
	private Object sEcho;//请求的次数
	private Object iTotalRecords; // 查询的记录数
	private Object iTotalDisplayRecords; // 过滤后的记录数
	private List<?> aaData;//返回的数据
	public DataTablesJSONReturn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DataTablesJSONReturn(Object sEcho, Object iTotalRecords, Object iTotalDisplayRecords, List<?> aaData) {
		super();
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.aaData = aaData;
	}

	public Object getsEcho() {
		return sEcho;
	}
	public void setsEcho(Object sEcho) {
		this.sEcho = sEcho;
	}
	public Object getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Object iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Object getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Object iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<?> getAaData() {
		return aaData;
	}
	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}
	@Override
	public String toString() {
		return "DataTablesJSONReturn [sEcho=" + sEcho + ", iTotalRecords=" + iTotalRecords + ", iTotalDisplayRecords="
				+ iTotalDisplayRecords + ", aaData=" + aaData + "]";
	}
	
}
