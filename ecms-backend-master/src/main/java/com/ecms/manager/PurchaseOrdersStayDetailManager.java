package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseOrdersStayDetailEntity;
import com.ecms.dal.mapper.PurchaseOrdersStayDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PurchaseOrdersStayDetailManager extends BaseManager<PurchaseOrdersStayDetailEntity> {
    @Resource
    private PurchaseOrdersStayDetailMapper mapper;
    @Override
    protected BasicMapper<PurchaseOrdersStayDetailEntity> getMapper() {
        return mapper;
    }
}
