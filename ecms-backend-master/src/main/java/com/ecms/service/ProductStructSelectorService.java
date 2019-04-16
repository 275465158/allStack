package com.ecms.service;

import com.ecms.bean.ProductStructOptionInfo;
import com.ecms.bean.ProductStructOptionRelationInfo;
import com.ecms.bean.ProductStructSelectorInfo;
import com.ecms.dal.entity.ProductStructOptionEntity;
import com.ecms.dal.entity.ProductStructRelationEntity;
import com.ecms.dal.entity.ProductStructSelectorEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductStructOptionManager;
import com.ecms.manager.ProductStructRelationManager;
import com.ecms.manager.ProductStructSelectorManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ProductStructSelectorService extends BaseService<ProductStructSelectorEntity, ProductStructSelectorInfo> {
    @Resource
    private ProductStructSelectorManager manager;
    @Resource
    private ProductStructOptionManager optionManager;
    @Resource
    private ProductStructRelationManager relationManager;
    @Override
    protected BaseManager<ProductStructSelectorEntity> getManager() {
        return manager;
    }

    @Transactional
    public void saveSelector(ProductStructSelectorInfo selectorInfo){
        ExchangeUtils.convertBlank2Null(selectorInfo);
        List<Map<String, String>> optionList = selectorInfo.getOptionList();
        List<Map<String, String>> selectList = selectorInfo.getSelectList();
        selectorInfo = saveOrUpdate(selectorInfo);
        if(optionList!=null && optionList.size()>0){
            for(int i=0; i<optionList.size(); i++){
                ProductStructOptionEntity optionEntity = new ProductStructOptionEntity();
                optionEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                optionEntity.setOptionValue(optionList.get(i).get("optionValue"));
                optionEntity.setSelectorId(selectorInfo.getId());
                optionEntity.setOrderBy(i);
                optionEntity.setStatus(1);
                optionManager.insertBy(optionEntity);
                for(int j = 0; j < selectList.size(); j++){
                    if(selectList.get(j).get("optionIndex") != null && i==Integer.parseInt(selectList.get(j).get("optionIndex"))){
                        ProductStructRelationEntity relationEntity = new ProductStructRelationEntity();
                        relationEntity.setSelectId(Integer.parseInt(selectList.get(j).get("selectId")));
                        relationEntity.setOptionId(optionEntity.getId());
                        relationEntity.setOrderBy(j);
                        relationEntity.setStatus(1);
                        relationEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        relationManager.insertBy(relationEntity);
                    }
                }
            }
        }
    }

    @Transactional
    public void modifySelector(ProductStructSelectorInfo selectorInfo){
        ExchangeUtils.convertBlank2Null(selectorInfo);
        List<Map<String, String>> optionList = selectorInfo.getOptionList();
        List<Map<String, String>> selectList = selectorInfo.getSelectList();
        selectorInfo = saveOrUpdate(selectorInfo);
        if(optionList != null && optionList.size()>0){
            for(int i = 0; i < optionList.size(); i++){
                ProductStructOptionEntity optionEntity = null;
                if (optionList.get(i).get("id") != null) {
                    optionEntity = optionManager.getEntityById(Integer.parseInt(optionList.get(i).get("id")));
                    optionEntity.setOptionValue(optionList.get(i).get("optionValue"));
                    optionManager.updateByPrimaryKeySelective(optionEntity);
                    for(int j = 0; j < selectList.size(); j++){
                        if(selectList.get(j).get("optionId")!=null && selectList.get(j).get("optionId").equals(optionList.get(i).get("id"))){
                            ProductStructRelationEntity searchRelation = new ProductStructRelationEntity();
                            searchRelation.setOptionId(Integer.parseInt(optionList.get(i).get("id")));
                            searchRelation.setSelectId(Integer.parseInt(selectList.get(j).get("selectId")));
                            List<ProductStructRelationEntity> existRelation = relationManager.getListBy(searchRelation);
                            if(existRelation.size()>0){
                                existRelation.get(0).setSelectId(Integer.parseInt(selectList.get(j).get("selectId")));
                                existRelation.get(0).setOrderBy(j);
                                relationManager.updateByPrimaryKeySelective(existRelation.get(0));
                            }else{
                                searchRelation.setOrderBy(j);
                                searchRelation.setStatus(1);
                                searchRelation.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                relationManager.insertBy(searchRelation);
                            }
                        }
                    }
                }else{
                    optionEntity = new ProductStructOptionEntity();
                    optionEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    optionEntity.setOptionValue(optionList.get(i).get("optionValue"));
                    optionEntity.setSelectorId(selectorInfo.getId());
                    optionEntity.setOrderBy(i);
                    optionEntity.setStatus(1);
                    optionManager.insertBy(optionEntity);
                    if(selectList!=null) {
                        for (int j = 0; j < selectList.size(); j++) {
                            if (selectList.get(j).get("optionIndex") != null && i == Integer.parseInt(selectList.get(j).get("optionIndex"))) {
                                ProductStructRelationEntity relationEntity = new ProductStructRelationEntity();
                                relationEntity.setSelectId(Integer.parseInt(selectList.get(j).get("selectId")));
                                relationEntity.setOptionId(optionEntity.getId());
                                relationEntity.setOrderBy(j);
                                relationEntity.setStatus(1);
                                relationEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                relationManager.insertBy(relationEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    public List<ProductStructSelectorInfo> getListExclude(Integer id) {
        ProductStructSelectorInfo searchSelector = new ProductStructSelectorInfo();
        searchSelector.setId(id);
        List<ProductStructSelectorInfo> selfList = this.getListBy(searchSelector);

        List<ProductStructSelectorInfo> list = this.getValidList();
        //移除id项
        if(selfList.size()>0) {
            list.removeAll(selfList);
        }
        return list;
    }
}
