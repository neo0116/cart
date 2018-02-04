package com.oracle.manager.service.serviceimpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.oracle.manager.mapper.ProductMapper;
import com.oracle.manager.mapper.ProductdescMapper;
import com.oracle.manager.pojo.Product;
import com.oracle.manager.pojo.Productdesc;
import com.oracle.manager.service.ProductService;
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductdescMapper productdescMapper;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static final ObjectMapper Mapper = new ObjectMapper();
	
	@Override
	public void save(Product product, String editorValue) {
		productMapper.insertSelective(product);
		Productdesc productdesc = new Productdesc();
		productdesc.setId(product.getId());
		productdesc.setProductdesc(editorValue);
		productdescMapper.insertSelective(productdesc);
	}

	@Override
	public Product displayProduct(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		System.out.println(id);
		System.out.println(product);
		return product;
	}

	@Override
	public void deleteBatch(List<Object> ids) {
		String topic = "delete";
		for (Object object : ids) {
			String idString = object.toString();
			Long id = Long.parseLong(idString);
			send( topic, id);
		}	
		
		super.deleteBatch(ids);
	}
	
	public void send(String topic,Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("topic",topic);
		map.put("id",id);
		String message = null;
		try {
			message = Mapper.writeValueAsString(map);
			rabbitTemplate.convertAndSend("product."+topic, message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
