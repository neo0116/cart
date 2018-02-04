package com.oracle.manager.service.serviceimpl;

import org.springframework.stereotype.Service;

import com.oracle.manager.service.TestService;
@Service
public class TestServiceImpl implements TestService{

	@Override
	public String getHello() {
		// TODO Auto-generated method stub
		return "你好";
	}

}
