package com.ecms.service;

import com.ecms.bean.ProductParameterSpecificationInfo;
import com.ecms.bean.ProductParameterSpecificationParam;
import com.ecms.dal.entity.ProductParameterSpecificationEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductParameterSpecificationManager;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductParameterSpecificationService extends BaseService<ProductParameterSpecificationEntity, ProductParameterSpecificationInfo> {
    @Resource
    private ProductParameterSpecificationManager productParameterSpecificationManager;

    @Override
    protected BaseManager<ProductParameterSpecificationEntity> getManager() {
        return productParameterSpecificationManager;
    }

    public void save(ProductParameterSpecificationParam param) {
        if (CollectionUtils.isEmpty(param.getParameterList())) {
            throw new CommonBusinessException("参数不能为空");
        }
        String userName = LoginFilter.getCurrentUser().getUserName();
        ProductParameterSpecificationInfo info = new ProductParameterSpecificationInfo();
        info.setName(param.getName());
        info.setMaintainer(userName);
        info.setId(param.getId());
        int size = param.getParameterList().size();
        info.setParameterNumber(size);
        StringBuilder name = new StringBuilder();
        StringBuilder unit = new StringBuilder();
        for (int i = 0; i < size; i++) {
            ProductParameterSpecificationParam.Parameter parameter = param.getParameterList().get(i);
            name.append(parameter.getParameterName());
            unit.append(parameter.getParameterUnit());
            if (i < size - 1) {
                name.append("*");
                unit.append("*");
            }
        }
        info.setParameterName(name.toString());
        info.setParameterUnit(unit.toString());
        if (param.getId() != null) {
            updateByPrimaryKeySelective(info);
        } else {
            info.setOwner(userName);
            insertBy(info);
        }
    }
}