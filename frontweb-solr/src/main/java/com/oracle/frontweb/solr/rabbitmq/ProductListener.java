package com.oracle.frontweb.solr.rabbitmq;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductListener {

	@Autowired
	private HttpSolrServer httpSolrServer;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public void delete(String message) {
		if (!StringUtils.isEmpty(message)) {
			try {
				JsonNode jsonNode = MAPPER.readTree(message);
				String id = jsonNode.get("id").asText();
				String topic = jsonNode.get("topic").asText();
				if (StringUtils.equals(topic, "delete") && !StringUtils.isEmpty(id)) {
					httpSolrServer.deleteById(id);
					httpSolrServer.commit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
