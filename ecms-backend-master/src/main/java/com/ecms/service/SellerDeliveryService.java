package com.ecms.service;

import com.ecms.bean.SellerDeliveryInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.SellerDeliveryEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SellerDeliveryManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SellerDeliveryService extends BaseService<SellerDeliveryEntity, SellerDeliveryInfo> {

	@Resource
	private SellerDeliveryManager sellerDeliveryManager;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseManager<SellerDeliveryEntity> getManager() {
        return sellerDeliveryManager;
    }

    @Override
    protected void doAfterUpdate(SellerDeliveryEntity entity) {
        redisCache.update(entity);
    }

    @PostConstruct
    public void init() {
        List<SellerDeliveryEntity> list = getValidStatusList();
        redisCache.refresh(list);
    }
}
