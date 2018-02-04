package com.oracle.frontweb.solr.service;

import com.oracle.frontweb.solr.pojo.SolrReturn;

public interface SolrService {

	 public  SolrReturn search(String queryString, Long cid, String price, Integer page,
			 String sort) throws Exception;

}
