package com.ecms.service;

import com.ecms.bean.ProductParameterNameInfo;
import com.ecms.dal.entity.ProductParameterNameEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductParameterNameManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ProductParameterNameService extends BaseService<ProductParameterNameEntity, ProductParameterNameInfo> {
    @Resource
    private ProductParameterNameManager productParameterNameManager;

    @Override
    protected BaseManager<ProductParameterNameEntity> getManager() {
        return productParameterNameManager;
    }

    @Override
    protected void setCustomProperties(List<ProductParameterNameInfo> list) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (ProductParameterNameInfo info : list) {
            info.setCreatedDate(format.format(info.getCreated()));
        }
    }
}