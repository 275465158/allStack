package com.ecms.service;

import com.ecms.bean.SellerInvoiceInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.SellerInvoiceEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SellerInvoiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SellerInvoiceService extends BaseService<SellerInvoiceEntity, SellerInvoiceInfo> {

	@Resource
	private SellerInvoiceManager sellerInvoiceManager;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseManager<SellerInvoiceEntity> getManager() {
        return sellerInvoiceManager;
    }

    @Override
    protected void doAfterUpdate(SellerInvoiceEntity entity) {
        redisCache.update(entity);
    }

    @PostConstruct
    public void init() {
        List<SellerInvoiceEntity> list = getValidStatusList();
        redisCache.refresh(list);
    }
}
