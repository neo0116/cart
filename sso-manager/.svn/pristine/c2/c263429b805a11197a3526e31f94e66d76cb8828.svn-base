package com.oracle.sso.service.impl;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.manager.mapper.UserMapper;
import com.oracle.manager.pojo.User;
import com.oracle.sso.service.UserService;
import com.oracle.sso.service.utils.redis.RedisUtils;
import com.oracle.sso.utils.redis.impl.ShardedJedisUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
	private ShardedJedisUtils shardedJedisUtils;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Boolean check(String param, Integer type) {
		if (type != 1 && type != 2 && type != 3) {
			throw new RuntimeException();
		}
		User user = new User();
		switch (type) {
		case 1:
			user.setUsername(param);
			break;
		case 2:
			user.setPhone(param);
			break;
		case 3:
			user.setEmail(param);
			break;
		default:
			break;
		}
		User one = userMapper.selectOne(user);
		return one == null;
	}

	@Override
	public Boolean register(User user) {
		String md5Hex = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Hex);
		int i = userMapper.insertSelective(user);
		return i == 1;
	}

	@Override
	public String login(String username, String password) throws Exception {
		//数据库优化，只用一个条件查
		User user = new User();
		user.setUsername(username);
		String md5Hex = DigestUtils.md5Hex(password);
		User user2 = userMapper.selectOne(user);
		if (user2!=null&&user2.getPassword().equals(md5Hex)) {
			String ticket = DigestUtils.md5Hex( UUID.randomUUID().toString() + username);
			String value = objectMapper.writeValueAsString(user2);
			redisUtils.setAndExpire(ticket, value, 1800);
			return ticket;
		}
		return null;
	}

	@Override
	public String queryUserByTicket(String ticket) throws Exception {
		if (!StringUtils.isEmpty(ticket)&&!"".equals(ticket)) {
			String jsonString = redisUtils.get(ticket);
			//String jsonString = shardedJedisUtils.get(ticket);
			redisUtils.expire(ticket, 1800);
			return jsonString;
		}
		return null;
	}

}
