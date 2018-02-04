package com.oracle.product.rabbitmq;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.product.redis.utils.RedisUtils;

public class RabbitMQListener {

	@Autowired
	private RedisUtils redisUtils;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public void delete(String message){
		try {
			if (!StringUtils.isEmpty(message)) {
				
				JsonNode jsonNode = MAPPER.readTree(message);
				String id = jsonNode.get("id").asText();
				String topic = jsonNode.get("topic").asText();
				if (StringUtils.equals(topic, "delete")&&!StringUtils.isEmpty(id)) {
					redisUtils.del("PRODUCT_REDIS_KEY"+id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
