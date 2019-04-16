package com.ecms.service;

import com.ecms.bean.ProductOriginalInfo;
import com.ecms.dal.entity.ProductOriginalEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductOriginalManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ProductOriginalService extends BaseService<ProductOriginalEntity, ProductOriginalInfo> {
	@Resource
	private ProductOriginalManager productOriginalManager;
	@Override
	protected BaseManager<ProductOriginalEntity> getManager() {
		// TODO Auto-generated method stub
		return productOriginalManager;
	}
}
