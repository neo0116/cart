package com.oracle.frontweb.redis.utils.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.oracle.frontweb.redis.utils.RedisUtils;

@Service
public class RedisUtilsImpl implements RedisUtils {

	@Autowired(required = false)
	ShardedJedisPool shardedJedisPool;

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
		return execute(new Function<String, ShardedJedis>() {

			@Override
			public String callback(ShardedJedis e) {
				return e.get(key);
			}
		});
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
				e.set(key, value);
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
