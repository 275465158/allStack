package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerPayTypeEntity;
import com.ecms.dal.mapper.SellerPayTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SellerPayTypeManager extends BaseManager<SellerPayTypeEntity> {

    @Resource
    private SellerPayTypeMapper sellerPayTypeMapper;

    @Override
    protected BasicMapper<SellerPayTypeEntity> getMapper() {
        return sellerPayTypeMapper;
    }
}
