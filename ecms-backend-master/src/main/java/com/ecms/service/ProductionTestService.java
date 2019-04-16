package com.ecms.service;

import com.ecms.bean.ProductionTestInfo;
import com.ecms.dal.entity.ProductionTestEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductionTestManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductionTestService extends BaseService<ProductionTestEntity, ProductionTestInfo> {

	@Resource
    private ProductionTestManager productionTestManager;

    @Override
    protected BaseManager<ProductionTestEntity> getManager() {
        return productionTestManager;
    }

}
