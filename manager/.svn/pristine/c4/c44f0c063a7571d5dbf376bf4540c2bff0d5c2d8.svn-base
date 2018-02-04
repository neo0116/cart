package com.oracle.manager.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.manager.mapper.ContentCategoryMapper;
import com.oracle.manager.pojo.Contentcategory;
import com.oracle.manager.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<Contentcategory> implements ContentCategoryService {

	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	@Override
	public void deleteByids(Long id) {
		List<Object> ids = new ArrayList<>();
		ids.add(id);
		findAllSubNodes(ids, id);
		for (Object object : ids) {
			contentCategoryMapper.deleteByPrimaryKey(object);
		}
	}

	private void findAllSubNodes(List<Object> ids, Long id) {
		Contentcategory contentcategory = new Contentcategory();
		contentcategory.setParentid(id);
		List<Contentcategory> list = contentCategoryMapper.select(contentcategory);
		//这里不加判断，他自己空的也不会循环，等于说结束递归
		if (!list.isEmpty()) {
			for (Contentcategory contentcategory2 : list) {
				ids.add(contentcategory2.getId());
				findAllSubNodes(ids, contentcategory2.getId());
			}
		}

	}

}
