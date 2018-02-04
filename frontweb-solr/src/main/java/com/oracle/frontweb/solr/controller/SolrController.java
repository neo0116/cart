package com.oracle.frontweb.solr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.frontweb.solr.pojo.SolrReturn;
import com.oracle.frontweb.solr.service.SolrService;

@Controller
public class SolrController {
	
	@Autowired
  	private  SolrService solrService;
	
	@RequestMapping("/search.html")
	public ModelAndView search(String queryString,Long cid,String price,Integer page,String sort){
		ModelAndView modelAndView = new ModelAndView("search");
		System.out.println(queryString);
		System.out.println(cid);
		System.out.println(price);
		System.out.println(page);
		System.out.println(sort);
		Integer p = null;
		try {
			SolrReturn solrReturn = solrService.search(queryString,cid,price,page,sort);
			modelAndView.addObject("queryString", queryString);
			modelAndView.addObject("cid", cid);
			modelAndView.addObject("price", price);
			modelAndView.addObject("page", page);
			modelAndView.addObject("sort", sort);
			modelAndView.addObject("result", solrReturn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
}
