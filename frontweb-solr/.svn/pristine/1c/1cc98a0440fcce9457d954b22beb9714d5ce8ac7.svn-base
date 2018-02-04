package com.oracle.frontweb.solr.pojo;

import java.io.Serializable;
import java.util.List;

public class SolrReturn implements Serializable{
	private Integer curPage;
	private Integer pageCount;
	private Integer recordCount;
	private List<?> productList;
	public SolrReturn() {
		super();
	}
	public SolrReturn(Integer curPage, Integer pageCount, Integer recordCount,
			List<?> productList) {
		super();
		this.curPage = curPage;
		this.pageCount = pageCount;
		this.recordCount = recordCount;
		this.productList = productList;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	public List<?> getProductList() {
		return productList;
	}
	public void setProductList(List<?> productList) {
		this.productList = productList;
	}
	@Override
	public String toString() {
		return "SolrReturn [curPage=" + curPage + ", pageCount=" + pageCount
				+ ", recordCount=" + recordCount + ", productList="
				+ productList + "]";
	}
	
}
