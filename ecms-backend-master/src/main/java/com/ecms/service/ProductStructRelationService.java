package com.ecms.service;

import com.ecms.bean.ProductStructOptionInfo;
import com.ecms.bean.ProductStructRelationInfo;
import com.ecms.bean.ProductStructSelectorInfo;
import com.ecms.dal.entity.ProductStructOptionEntity;
import com.ecms.dal.entity.ProductStructRelationEntity;
import com.ecms.dal.entity.ProductStructSelectorEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductStructOptionManager;
import com.ecms.manager.ProductStructRelationManager;
import com.ecms.manager.ProductStructSelectorManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductStructRelationService extends BaseService<ProductStructRelationEntity, ProductStructRelationInfo> {
    @Resource
    private ProductStructRelationManager manager;
    @Resource
    private ProductStructSelectorManager selectorManager;
    @Resource
    private ProductStructOptionManager optionManager;
    @Override
    protected BaseManager<ProductStructRelationEntity> getManager() {
        return manager;
    }

    public List<ProductStructRelationInfo> searchList(Integer optionId){
        ProductStructRelationEntity searchEntity = new ProductStructRelationEntity();
        searchEntity.setOptionId(optionId);
        List<ProductStructRelationEntity> existList = manager.getListBy(searchEntity);
        List<ProductStructRelationInfo> relationInfos = ExchangeUtils.exchangeList(existList, ProductStructRelationInfo.class);
        for(int i = 0; i < relationInfos.size(); i++){
            ProductStructSelectorEntity searchSelector = new ProductStructSelectorEntity();
            searchSelector.setId(relationInfos.get(i).getSelectId());
            List<ProductStructSelectorInfo> selectorInfos = ExchangeUtils.exchangeList(selectorManager.getListBy(searchSelector), ProductStructSelectorInfo.class);
            relationInfos.get(i).setSelectorInfo(selectorInfos.get(0));
            ProductStructOptionEntity searchOption = new ProductStructOptionEntity();
            searchOption.setId(relationInfos.get(i).getOptionId());
            List<ProductStructOptionInfo> optionInfos = ExchangeUtils.exchangeList(optionManager.getListBy(searchOption), ProductStructOptionInfo.class);
            relationInfos.get(i).setOptionInfo(optionInfos.get(0));
        }
        return relationInfos;
    }
}
