package com.oracle.sso.service.utils.redis;

public interface RedisUtils {

	String set(String key, String value);

	String get(String key);
	String get(String key,String aa);

	Long del(String key);

	Long setAndExpire(String key, String value, Integer timeout);

	Long expire(String key, Integer timeout);

}
