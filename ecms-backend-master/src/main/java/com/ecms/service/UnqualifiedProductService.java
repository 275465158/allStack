package com.ecms.service;

import com.ecms.bean.UnqualifiedProductInfo;
import com.ecms.dal.entity.UnqualifiedHandleEntity;
import com.ecms.dal.entity.UnqualifiedProductEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.UnqualifiedProductManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UnqualifiedProductService extends BaseService<UnqualifiedProductEntity, UnqualifiedProductInfo> {
    @Resource
    private UnqualifiedProductManager manager;
    @Override
    protected BaseManager<UnqualifiedProductEntity> getManager() {
        return manager;
    }

    public boolean isExist(UnqualifiedProductInfo info){
        boolean isExist = false;
        UnqualifiedProductEntity searchEntity = ExchangeUtils.exchangeObject(info, UnqualifiedProductEntity.class);
        List<UnqualifiedProductEntity> entities = manager.getListBy(searchEntity);
        if(entities.size()>0){
            isExist = true;
        }
        return isExist;
    }
}
