package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductAcceptanceDetailEntity;
import com.ecms.dal.mapper.ProductAcceptanceDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ProductAcceptanceDetailManager extends BaseManager<ProductAcceptanceDetailEntity> {
    @Resource
    private ProductAcceptanceDetailMapper productAcceptanceDetailMapper;

    @Override
    protected BasicMapper<ProductAcceptanceDetailEntity> getMapper() {
        return productAcceptanceDetailMapper;
    }

    public List<ProductAcceptanceDetailEntity> getByAcceptanceId(int acceptanceId) {
        return productAcceptanceDetailMapper.selectByAcceptanceId(acceptanceId);
    }

    public void deleteByAcceptanceId(int acceptanceId) {
        productAcceptanceDetailMapper.deleteByAcceptanceId(acceptanceId);
    }
}