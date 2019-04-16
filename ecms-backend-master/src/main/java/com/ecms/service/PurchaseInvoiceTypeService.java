package com.ecms.service;

import com.ecms.bean.PurchaseInvoiceTypeInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.PurchaseInvoiceTypeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.PurchaseInvoiceTypeManager;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseInvoiceTypeService extends BaseService<PurchaseInvoiceTypeEntity, PurchaseInvoiceTypeInfo> {

    @Resource
    private RedisCache redisCache;

    @PostConstruct
    public void init() {
        List<PurchaseInvoiceTypeEntity> list = getValidStatusList();
        redisCache.refresh(list);
    }

    @Resource
    private PurchaseInvoiceTypeManager typeManager;

    @Override
    protected BaseManager<PurchaseInvoiceTypeEntity> getManager() {
        return typeManager;
    }

    @Override
    public List<PurchaseInvoiceTypeEntity> getValidStatusList() {
        Condition condition = new Condition(eClass);
        Example.Criteria criteria = condition.createCriteria();
        return getManager().getByCondition(condition);
    }

    @Override
    protected void doAfterUpdate(PurchaseInvoiceTypeEntity entity) {
        redisCache.update(entity);
    }

    public void updateStatus(int id, int status) {
        PurchaseInvoiceTypeEntity typeEntity = new PurchaseInvoiceTypeEntity();
        typeEntity.setId(id);
        typeEntity.setStatus(status);
        typeEntity.setUpdated(new Date());
        typeManager.updateByPrimaryKeySelective(typeEntity);
        doAfterUpdate(typeEntity);
    }

}
