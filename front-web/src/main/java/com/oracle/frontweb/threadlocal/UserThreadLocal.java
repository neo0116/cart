package com.oracle.frontweb.threadlocal;

import com.oracle.manager.pojo.User;

public class UserThreadLocal {
	public static ThreadLocal<User> threadLocal = new ThreadLocal<User>();
	public static void set(User user){
		threadLocal.set(user);
	}
	public static User get(){
		return threadLocal.get();
	}
}