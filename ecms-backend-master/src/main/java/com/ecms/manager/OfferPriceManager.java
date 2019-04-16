package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.OfferPriceEntity;
import com.ecms.dal.mapper.OfferPriceMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class OfferPriceManager extends BaseManager<OfferPriceEntity>{
    @Resource
    private OfferPriceMapper mapper;
    @Override
    protected BasicMapper<OfferPriceEntity> getMapper() {
        return mapper;
    }
}
