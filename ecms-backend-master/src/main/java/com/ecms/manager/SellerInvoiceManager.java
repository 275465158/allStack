package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerInvoiceEntity;
import com.ecms.dal.mapper.SellerInvoiceMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SellerInvoiceManager extends BaseManager<SellerInvoiceEntity> {

    @Resource
    private SellerInvoiceMapper sellerInvoiceMapper;

    @Override
    protected BasicMapper<SellerInvoiceEntity> getMapper() {
        return sellerInvoiceMapper;
    }
}
