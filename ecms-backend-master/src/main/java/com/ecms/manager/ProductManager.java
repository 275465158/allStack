package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductEntity;
import com.ecms.dal.entity.ProductionPlanEntity;
import com.ecms.dal.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductManager extends BaseManager<ProductionPlanEntity>{

	@Resource
	private ProductMapper productMapper;

	@Override
	protected BasicMapper<ProductionPlanEntity> getMapper() {
		return productMapper;
	}


    public void updateProduct(ProductionPlanEntity productionPlanEntity) {
		productMapper.updateByPrimaryKeySelective(productionPlanEntity);
    }

    public String getCheckOrder(){
		return productMapper.getCheckOrder();
	}
}
