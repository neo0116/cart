package com.oracle.product.redis.utils.impl;

//回调函数：重写这个callback返回实际需要的T
public interface Function<T,E> {
	public  T callback(E e);
}
