package com.ecms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.ProductAcceptanceDetailInfo;
import com.ecms.dal.entity.ProductAcceptanceDetailEntity;
import com.ecms.manager.BaseManager;
@Service
public class ProductAcceptanceDetailService extends BaseService<ProductAcceptanceDetailEntity,ProductAcceptanceDetailInfo> {
	@Resource
	private BaseManager<ProductAcceptanceDetailEntity> ProductAcceptanceDetailManager;

	@Override
	protected BaseManager<ProductAcceptanceDetailEntity> getManager() {
		// TODO Auto-generated method stub
		return ProductAcceptanceDetailManager;
	}

}
