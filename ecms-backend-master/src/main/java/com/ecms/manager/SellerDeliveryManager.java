package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerDeliveryEntity;
import com.ecms.dal.mapper.SellerDeliveryMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SellerDeliveryManager extends BaseManager<SellerDeliveryEntity> {

    @Resource
    private SellerDeliveryMapper sellerDeliveryMapper;

    @Override
    protected BasicMapper<SellerDeliveryEntity> getMapper() {
        return sellerDeliveryMapper;
    }
}
