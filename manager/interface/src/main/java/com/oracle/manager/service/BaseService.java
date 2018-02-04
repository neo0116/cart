package com.oracle.manager.service;

import java.util.List;

public interface BaseService<T> {
	
	public Integer selectCount();
	
	public Integer selectCount(T t);
	
	public T selectById(Long id);
	
	public List<T> select(T t);
	
	public List<T> selectAll();
	
	public T selectOne(T t);
	
	public void save(T t);
	
	public void saveSelective(T t);
	
	public void update(T t);
	
	public void updateSelective(T t);
	
	public void delete(Long id);
	
	public void deleteBatch(List<Object> ids);
	
	public List<T> selectPages(Integer num,Integer rows);
}


