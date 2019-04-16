package com.ecms.service;

import com.ecms.bean.SellerPayTypeInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.SellerPayTypeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SellerPayTypeManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SellerPayTypeService extends BaseService<SellerPayTypeEntity, SellerPayTypeInfo> {

	@Resource
	private SellerPayTypeManager sellerPayTypeManager;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseManager<SellerPayTypeEntity> getManager() {
        return sellerPayTypeManager;
    }

    @Override
    protected void doAfterUpdate(SellerPayTypeEntity entity) {
        redisCache.update(entity);
    }

    @PostConstruct
    public void init() {
        List<SellerPayTypeEntity> list = getValidStatusList();
        redisCache.refresh(list);
    }
}
