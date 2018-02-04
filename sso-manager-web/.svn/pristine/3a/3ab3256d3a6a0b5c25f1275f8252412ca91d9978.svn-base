/**
 * 
 */
package com.oracle.manager.sso.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.manager.pojo.User;
import com.oracle.sso.service.UserService;

/**
 * @author WFQ
 * @date 2018年2月3日 下午6:03:14 
 * @version  V1.0  
 */

@RequestMapping("/user")
@Controller
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@RequestMapping(value="/check/{param}/{type}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> check(
			@PathVariable()String param,
			@PathVariable("type") Integer type){
		try {
			Boolean b =  userService.check(param, type);
			if (b) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> register(@Valid User user,BindingResult br){
		boolean hasErrors = br.hasErrors();
		if (hasErrors) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		}
		try {
			Boolean b = userService.register(user);
			if (b) {
				return ResponseEntity.status(HttpStatus.CREATED).body(true);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> login(
			@RequestParam("username") String username,
			@RequestParam("password") String password){
		try {
			String ticket = userService.login(username, password);
			if (StringUtils.isNotBlank(ticket)) {
				return ResponseEntity.status(HttpStatus.OK).body(ticket);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	@RequestMapping(value="{ticket}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> queryUserByTicket(@PathVariable("ticket") String ticket){
		try {
			String jsonData = userService.queryUserByTicket(ticket);
			if (StringUtils.isNotBlank(jsonData)) {
				User user = MAPPER.readValue(jsonData, User.class);
				return ResponseEntity.status(HttpStatus.OK).body(user);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
