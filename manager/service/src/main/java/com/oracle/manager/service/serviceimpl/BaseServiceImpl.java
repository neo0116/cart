package com.oracle.manager.service.serviceimpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.oracle.manager.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T>{
	
	@Autowired
	private Mapper<T> mapper;
	
	Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType pType = (ParameterizedType) type;
		Type[] types = pType.getActualTypeArguments();
		clazz = (Class<T>) types[0];
	}

	@Override
	public T selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> select(T t) {
		List<T> list = mapper.select(t);
		return list;
	}

	@Override
	public List<T> selectAll() {
		List<T> list = mapper.select(null);
		return list;
	}

	@Override
	public T selectOne(T t) {
		T selectOne = mapper.selectOne(t);
		return selectOne;
	}

	@Override
	public void save(T t) {
		mapper.insert(t);
	}

	@Override
	public void saveSelective(T t) {
		mapper.insertSelective(t);
	}

	@Override
	public void update(T t) {
		mapper.updateByPrimaryKey(t);
	}

	@Override
	public void updateSelective(T t) {
		mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void delete(Long id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteBatch(List<Object> ids) {
		Example example = new Example(clazz);
		example.createCriteria().andIn("id", ids);
		mapper.deleteByExample(example);
	}

	@Override
	public List<T> selectPages(Integer num, Integer rows) {
		Page page = PageHelper.startPage(num, rows);
		List<T> list = mapper.select(null);
		return list;
	}

	@Override
	public Integer selectCount() {
		int count = mapper.selectCount(null);
		return count;
	}

	@Override
	public Integer selectCount(T t) {
		int count = mapper.selectCount(t);
		return count;
	}

}
