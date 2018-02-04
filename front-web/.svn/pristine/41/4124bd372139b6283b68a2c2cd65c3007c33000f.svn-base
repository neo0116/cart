package com.oracle.frontweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.commons.pojo.HttpClientReturn;
import com.oracle.frontweb.httpclient.service.HttpClientUtils;
import com.oracle.manager.pojo.Content;

@Controller
public class FrontWebController {

	@Autowired
	private HttpClientUtils httpClientUtils;

	@Value("${bigADList.URL}")
	private String BIG_AD_LIST_URL;

	@Value("${smallADList.URL}")
	private String SMALL_AD_LIST_URL;

	public static final ObjectMapper ObjectMapper = new ObjectMapper();

	@RequestMapping(value = "/*.html", method = RequestMethod.GET)
	public ModelAndView frontweb() {
		ModelAndView modelAndView = new ModelAndView("index");
//		System.out.println(BIG_AD_LIST_URL);
		try {
			HttpClientReturn httpClientReturn = httpClientUtils.doGet(BIG_AD_LIST_URL);
			if (httpClientReturn!=null) {
				String responseBody = httpClientReturn.getResponseBody();
				JavaType valueType = ObjectMapper.getTypeFactory().constructCollectionType(List.class, Content.class);
				List<Content> bigADList = ObjectMapper.readValue(responseBody, valueType);
				modelAndView.addObject("bigADList", bigADList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		try {
			HttpClientReturn httpClientReturn = httpClientUtils.doGet(SMALL_AD_LIST_URL);
			if (httpClientReturn!=null) {
				String responseBody = httpClientReturn.getResponseBody();
				JavaType valueType = ObjectMapper.getTypeFactory().constructCollectionType(List.class, Content.class);
				List<Content> smallADList = ObjectMapper.readValue(responseBody, valueType);
				modelAndView.addObject("smallADList", smallADList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;
	}
}
