package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerCustomerContacterEntity;
import com.ecms.dal.mapper.SellerCustomerContacterMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/11 17:06
 */
@Component
public class SellerCustomerContacterManager extends BaseManager<SellerCustomerContacterEntity> {
    @Resource
    private SellerCustomerContacterMapper sellerCustomerContacterMapper;
    @Override
    protected BasicMapper<SellerCustomerContacterEntity> getMapper() {
        return sellerCustomerContacterMapper;
    }
}
