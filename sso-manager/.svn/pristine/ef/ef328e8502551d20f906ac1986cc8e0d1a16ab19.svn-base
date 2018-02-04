package com.oracle.sso.utils.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.sso.service.utils.redis.RedisUtils;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisUtilsImpl implements RedisUtils {

	@Autowired(required = false)
	public ShardedJedisPool shardedJedisPool;

	public <T> T execute(Function<T, ShardedJedis> rsj) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return rsj.callback(shardedJedis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		}
		shardedJedisPool.close();
		return null;

	}

	@Override
	public String set(final String key, final String value) {

		return execute(new Function<String, ShardedJedis>() {

			@Override
			public String callback(ShardedJedis e) {
				return e.set(key, value);
			}

		});
	}

	@Override
	public String get(final String key) {
		System.out.println(key);
		return execute(new Function<String, ShardedJedis>() {

			@Override
			public String callback(ShardedJedis e) {
				System.out.println( e);
				System.out.println(key);
				String string = e.get(key);
				System.out.println(string);
				return e.get(key);
			}
		});
	}
	
	@Override
	public String get(final String key,String aa) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		System.out.println(key+"~~~~~~~~~~~~~~~~~~~~~");
		String string = shardedJedis.get(key);
		System.out.println(string+"====================");
		shardedJedis.close();
		return string;
		
	}

	@Override
	public Long del(final String key) {
		return execute(new Function<Long, ShardedJedis>() {

			@Override
			public Long callback(ShardedJedis e) {
				return e.del(key);
			}
		});
	}

	@Override
	public Long setAndExpire(final String key, final String value, final Integer timeout) {
		return execute(new Function<Long, ShardedJedis>() {

			@Override
			public Long callback(ShardedJedis e) {
				String set = e.set(key, value);
				System.out.println(key);
				System.out.println(set);
				return e.expire(key, timeout);
			}
		});
	}

	@Override
	public Long expire(final String key, final Integer timeout) {
		return execute(new Function<Long, ShardedJedis>() {

			@Override
			public Long callback(ShardedJedis e) {
				return e.expire(key, timeout);
			}
		});
	}

}
