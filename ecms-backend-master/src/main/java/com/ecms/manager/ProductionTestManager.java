package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionTestEntity;
import com.ecms.dal.mapper.ProductionTestMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductionTestManager extends BaseManager<ProductionTestEntity> {

	@Resource
	private ProductionTestMapper productionTestMapper;

    @Override
    protected BasicMapper<ProductionTestEntity> getMapper() {
        return productionTestMapper;
    }

}
