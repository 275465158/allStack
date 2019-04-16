package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.*;
import com.ecms.dal.mapper.SellerOrderProductMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SellerOrderProductManager extends BaseManager<SellerOrderProductEntity> {

    @Resource
    private SellerOrderProductMapper sellerOrderProductMapper;
    @Override
    protected BasicMapper<SellerOrderProductEntity> getMapper() {
        return sellerOrderProductMapper;
    }

    public void updateSellerOrderProductStatusByOrderId(Integer orderId){
        sellerOrderProductMapper.updateSellerOrderProductStatusByOrderId(orderId);
    }

    public List<Integer> getListByStatus(Integer productId){
        return sellerOrderProductMapper.getListByStatus(productId);
    }
}
