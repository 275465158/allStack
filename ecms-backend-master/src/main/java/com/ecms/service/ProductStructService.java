package com.ecms.service;

import com.ecms.bean.ProductStructInfo;
import com.ecms.bean.ProductStructParameterInfo;
import com.ecms.dal.entity.ProductStructEntity;
import com.ecms.dal.entity.ProductStructParameterEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductStructManager;
import com.ecms.manager.ProductStructParameterManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ProductStructService extends BaseService<ProductStructEntity, ProductStructInfo> {

    @Resource
    private ProductStructManager manager;
    @Resource
    private ProductStructParameterManager parameterManager;
    @Override
    protected BaseManager<ProductStructEntity> getManager() {
        return manager;
    }

    @Transactional
    public ProductStructInfo saveStruct(ProductStructInfo structInfo) {
        ExchangeUtils.convertBlank2Null(structInfo);
        List<Map<String, String>> paramList = structInfo.getParams();
        structInfo = saveOrUpdate(structInfo);
        for(int i = 0; i < paramList.size(); i++){
            ProductStructParameterEntity paramEntity = new ProductStructParameterEntity();
            paramEntity.setOrderBy(i);
            paramEntity.setSelectId(Integer.parseInt(paramList.get(i).get("selectId")));
            paramEntity.setStructId(structInfo.getId());
            paramEntity.setStatus(1);
            paramEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            parameterManager.insertBy(paramEntity);
        }
        return structInfo;
    }

    @Transactional
    public ProductStructInfo modifyStruct(ProductStructInfo structInfo){
        ExchangeUtils.convertBlank2Null(structInfo);
        List<Map<String, String>> paramList = structInfo.getParams();
        structInfo = saveOrUpdate(structInfo);
        for(int i = 0; i < paramList.size(); i++){
            ProductStructParameterEntity paramEntity = null;
            if(paramList.get(i).get("id")!=null){
                paramEntity = parameterManager.getEntityById(Integer.parseInt(paramList.get(i).get("id")));
                paramEntity.setSelectId(Integer.parseInt(paramList.get(i).get("selectId")));
                parameterManager.updateByPrimaryKeySelective(paramEntity);
            }else{
                paramEntity = new ProductStructParameterEntity();
                paramEntity.setOrderBy(i);
                paramEntity.setSelectId(Integer.parseInt(paramList.get(i).get("selectId")));
                paramEntity.setStructId(structInfo.getId());
                paramEntity.setStatus(1);
                paramEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                parameterManager.insertBy(paramEntity);
            }
        }
        return structInfo;
    }
}
