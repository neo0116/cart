package com.oracle.manager.service;

import com.oracle.manager.pojo.Contentcategory;

public interface ContentCategoryService extends BaseService<Contentcategory>{

	void deleteByids(Long id);

}
