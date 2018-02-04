package com.oracle.sso.utils.redis.impl;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardedJedisUtils {
	private ShardedJedisPool shardedJedisPool;
	private ShardedJedis shardedJedis;
	public ShardedJedisUtils(ShardedJedisPool shardedJedisPool){
		shardedJedis = shardedJedisPool.getResource();
		
		
	}
	
	public String set(String key,String value){
		try {
			return shardedJedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (shardedJedis!=null) {
			shardedJedisPool.returnResourceObject(shardedJedis);
		}
		shardedJedisPool.close();
		return null;
		
	}
	public String get(String key){
		try {
			System.out.println( shardedJedis.get(key));
			return shardedJedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (shardedJedis!=null) {
			shardedJedisPool.returnResourceObject(shardedJedis);
		}
		shardedJedisPool.close();
		return null;
	}
	public Long del(String key){
		try {
			return shardedJedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (shardedJedis!=null) {
			shardedJedisPool.returnResourceObject(shardedJedis);
		}
		shardedJedisPool.close();
		return null;
	}
	public Long setAndExpire(String key,String value,Integer timeout){
		try {
			shardedJedis.set(key, value);
			return shardedJedis.expire(key, timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (shardedJedis!=null) {
			shardedJedisPool.returnResourceObject(shardedJedis);
		}
		shardedJedisPool.close();
		return null;
	}
	public Long expire(String key,Integer timeout){
		try {
			return shardedJedis.expire(key, timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (shardedJedis!=null) {
			shardedJedisPool.returnResourceObject(shardedJedis);
		}
		shardedJedisPool.close();
		return null;
	}
	public void end(){
		if (shardedJedis!=null) {
			shardedJedisPool.returnResourceObject(shardedJedis);
		}
		shardedJedisPool.close();
	}
}
