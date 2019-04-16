package com.ecms.service;

import com.ecms.bean.ProductUnitInfo;
import com.ecms.dal.entity.ProductUnitEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductUnitManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ProductUnitService extends BaseService<ProductUnitEntity, ProductUnitInfo> {
	@Resource
	private ProductUnitManager productUnitManager;
	@Override
	protected BaseManager<ProductUnitEntity> getManager() {
		// TODO Auto-generated method stub
		return productUnitManager;
	}
}
