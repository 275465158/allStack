package com.ecms.service;

import com.ecms.bean.ProductNameInfo;
import com.ecms.dal.entity.ProductNameEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductNameManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ProductNameService extends BaseService<ProductNameEntity, ProductNameInfo> {
	@Resource
	private ProductNameManager productNameManager;
	@Override
	protected BaseManager<ProductNameEntity> getManager() {
		// TODO Auto-generated method stub
		return productNameManager;
	}
}
