package com.ecms.service;

import com.ecms.bean.ProductStructOptionInfo;
import com.ecms.bean.ProductStructRelationInfo;
import com.ecms.dal.entity.ProductStructOptionEntity;
import com.ecms.dal.entity.ProductStructRelationEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductStructOptionManager;
import com.ecms.manager.ProductStructRelationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductStructOptionService extends BaseService<ProductStructOptionEntity, ProductStructOptionInfo> {
    @Resource
    private ProductStructOptionManager manager;
    @Resource
    private ProductStructRelationManager relationManager;

    @Override
    protected BaseManager<ProductStructOptionEntity> getManager() {
        return manager;
    }

    @Override
    public int deleteBy(Integer id) {
        ProductStructRelationEntity searchRelation = new ProductStructRelationEntity();
        searchRelation.setOptionId(id);
        List<ProductStructRelationEntity> preDeleteList = relationManager.getListBy(searchRelation);
        for(int i = 0; i < preDeleteList.size(); i++){
            relationManager.deleteByPrimaryKey(preDeleteList.get(i).getId());
        }
        return manager.deleteByPrimaryKey(id);
    }
}
