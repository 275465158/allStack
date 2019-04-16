package com.ecms.service;

import com.ecms.bean.SellerPayRuleInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.SellerPayRuleEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SellerPayRuleManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SellerPayRuleService extends BaseService<SellerPayRuleEntity, SellerPayRuleInfo> {

	@Resource
	private SellerPayRuleManager sellerPayRuleManager;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseManager<SellerPayRuleEntity> getManager() {
        return sellerPayRuleManager;
    }

    @Override
    protected void doAfterUpdate(SellerPayRuleEntity entity) {
        redisCache.update(entity);
    }

    @PostConstruct
    public void init() {
        List<SellerPayRuleEntity> list = getValidStatusList();
        redisCache.refresh(list);
    }
}
