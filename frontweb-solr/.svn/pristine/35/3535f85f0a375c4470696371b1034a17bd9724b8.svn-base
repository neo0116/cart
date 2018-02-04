package com.oracle.frontweb.solr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.frontweb.solr.pojo.Product;
import com.oracle.frontweb.solr.pojo.SolrReturn;
import com.oracle.frontweb.solr.service.SolrService;


@Service
public class SolrServiceImpl implements SolrService{
	
	
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	@Override
	public SolrReturn search(String queryString, Long cid, String price,
			Integer page, String sort) throws Exception {
		SolrQuery query = new SolrQuery();
		if (StringUtils.isNoneEmpty(queryString)) {
			query.setQuery("title:"+queryString);
		}else {
			query.setQuery("*:*");
		}
		if (cid!=null) {
			query.setFilterQueries("cid:"+cid);
		}
		if (StringUtils.isNoneEmpty(price)) {//0-9
			String[] split = price.split("-");
			query.setFilterQueries("price:["+split[0]+" TO "+split[1]+"]");
		}
		if (page==null) {
			page=1;
		}
		query.setStart((page-1)*25);
		query.setRows(25);
		if (StringUtils.isNoneEmpty(sort)&&"1".equals(sort)) {
			query.setSort("id", ORDER.desc);
		}else{
			query.setSort("id", ORDER.asc);
		}
		query.setHighlight(true);
		query.setHighlightSimplePre("<span style='color:red'>");
		query.setHighlightSimplePost("</span>");
		query.addHighlightField("title");
		QueryResponse response = httpSolrServer.query(query);
		SolrDocumentList solrDocumentList = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		
		
		long recordCount = solrDocumentList.getNumFound();
		List<Product> productList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			long id =  Long.parseLong((String) solrDocument.get("id"));
			String title = (String) solrDocument.get("title");
			String sellpoint = (String) solrDocument.get("sellpoint");
			long price1 = (long) solrDocument.get("price");
			Integer num = (Integer) solrDocument.get("num");
			String image = (String) solrDocument.get("image");
			long cid1 = (long) solrDocument.get("cid");
			Integer status = (Integer) solrDocument.get("status");
			Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
			List<String> list = map.get("title");
			if (list!=null) {
				title = list.get(0);
			}
			
			
			Product product = new Product();
			product.setId(id);
			product.setTitle(title);
			product.setSellpoint(sellpoint);
			product.setPrice(price1);
			product.setNum(num);
			product.setImage(image);
			product.setCid(cid1);
			product.setStatus(status==0?false:true);
			productList.add(product);
		}
		Integer pageCount = (int) ((recordCount+25-1)/25);
		SolrReturn solrReturn = new SolrReturn(page, pageCount, (int) recordCount, productList);
		return solrReturn;
	}

}
