package com.ecms.service;

import com.ecms.bean.*;
import com.ecms.dal.entity.ProductStructOptionEntity;
import com.ecms.dal.entity.ProductStructParameterEntity;
import com.ecms.dal.entity.ProductStructRelationEntity;
import com.ecms.dal.entity.ProductStructSelectorEntity;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductStructParameterService extends BaseService<ProductStructParameterEntity, ProductStructParameterInfo> {

    @Resource
    private ProductStructParameterManager manager;
    @Resource
    private ProductStructSelectorManager selectorManager;
    @Resource
    private ProductStructOptionManager optionManager;
    @Resource
    private ProductStructRelationManager relationManager;
    @Override
    protected BaseManager<ProductStructParameterEntity> getManager() {
        return manager;
    }

    @Override
    public List<ProductStructParameterInfo> getListBy(ProductStructParameterInfo info){
        ProductStructParameterEntity entity = ExchangeUtils.exchangeObject(info, ProductStructParameterEntity.class);
        List<ProductStructParameterInfo> list = ExchangeUtils.exchangeList(getManager().getListBy(entity), ProductStructParameterInfo.class);
        for(int i = 0; i < list.size(); i++){
            Integer selectId = list.get(i).getSelectId();
            ProductStructSelectorInfo selectorInfo = ExchangeUtils.exchangeObject(selectorManager.getEntityById(selectId), ProductStructSelectorInfo.class);
            ProductStructOptionEntity searchOption = new ProductStructOptionEntity();
            searchOption.setSelectorId(selectId);
            List<ProductStructOptionEntity> optionEntityList = optionManager.getListBy(searchOption);
            List<ProductStructOptionInfo> optionList = ExchangeUtils.exchangeList(optionEntityList, ProductStructOptionInfo.class);
            for(int j = 0; j < optionList.size(); j++){
                ProductStructRelationEntity searchRelation = new ProductStructRelationEntity();
                searchRelation.setOptionId(optionList.get(j).getId());
                List<ProductStructRelationInfo> relationInfos = ExchangeUtils.exchangeList(relationManager.getListBy(searchRelation), ProductStructRelationInfo.class);
                for(int index_rel = 0 ; index_rel < relationInfos.size(); index_rel++){
                    ProductStructSelectorEntity searchSelector = new ProductStructSelectorEntity();
                    searchSelector.setId(relationInfos.get(index_rel).getSelectId());
                    List<ProductStructSelectorInfo> selectorInfos = ExchangeUtils.exchangeList(selectorManager.getListBy(searchSelector), ProductStructSelectorInfo.class);
                    for(int index_sel = 0; index_sel < selectorInfos.size(); index_sel++){
                        ProductStructOptionEntity searchOptionEntity = new ProductStructOptionEntity();
                        searchOptionEntity.setSelectorId(selectorInfos.get(index_sel).getId());
                        List<ProductStructOptionInfo> optionInfos = ExchangeUtils.exchangeList(optionManager.getListBy(searchOptionEntity), ProductStructOptionInfo.class);
                        selectorInfos.get(index_sel).setOptions(optionInfos);
                    }
                    if(selectorInfos.size()>0) {
                        relationInfos.get(index_rel).setSelectorInfo(selectorInfos.get(0));
                    }
                }
                optionList.get(j).setRelationInfos(relationInfos);
            }
            selectorInfo.setOptions(optionList);
            list.get(i).setSelectorInfo(selectorInfo);
        }
        return ExchangeUtils.exchangeList(list, iClass);
    }
}
