package com.ecms.web.controller;

import com.ecms.bean.ProductParameterNameInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductParameterNameEntity;
import com.ecms.service.ProductParameterNameService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("productParameterName")
public class ProductParameterNameController extends BaseController<ProductParameterNameEntity, ProductParameterNameInfo> {
    @Resource
    private ProductParameterNameService productParameterNameService;

    @Override
    protected com.ecms.service.BaseService<ProductParameterNameEntity, ProductParameterNameInfo> getBaseService() {
        return productParameterNameService;
    }

    @RequestMapping("/searchList")
    public Result<List<ProductParameterNameInfo>> searchList(){
        ProductParameterNameInfo parameterNameInfo = new ProductParameterNameInfo();
        parameterNameInfo.setStatus(1);
        List<ProductParameterNameInfo> list = productParameterNameService.getListBy(parameterNameInfo);
        return ResultUtils.success(list);
    }

    @RequestMapping("save")
    public Result<Void> save(ProductParameterNameInfo info) {
        info.setOwner(LoginFilter.getCurrentUser().getUserName());
        getBaseService().insertBy(info);
        return ResultUtils.success();
    }

}