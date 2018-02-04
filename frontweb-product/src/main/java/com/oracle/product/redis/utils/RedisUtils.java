package com.oracle.product.redis.utils;

public interface RedisUtils {
	//添加
	public String set(String key,String value);
	//获取
	public String get(String key);
	//删除
	public Long del(String key);
	//创建key，并且设置存活时间
	public Long setAndExpire(String key,String value,Integer timeout);
	//设置存活时间
	public Long expire(String key,Integer timeout);
}
