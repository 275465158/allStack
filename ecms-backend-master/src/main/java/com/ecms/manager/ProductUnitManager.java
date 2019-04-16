package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductUnitEntity;
import com.ecms.dal.mapper.ProductUnitMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductUnitManager extends BaseManager<ProductUnitEntity> {
	@Resource
	private ProductUnitMapper productUnitMapper ;
	@Override
	protected BasicMapper<ProductUnitEntity> getMapper() {
		// TODO Auto-generated method stub
		return productUnitMapper;
	}
}
