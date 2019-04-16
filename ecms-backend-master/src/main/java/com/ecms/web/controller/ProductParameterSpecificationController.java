package com.ecms.web.controller;

import com.ecms.bean.ProductParameterSpecificationInfo;
import com.ecms.bean.ProductParameterSpecificationParam;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductParameterSpecificationEntity;
import com.ecms.service.ProductParameterSpecificationService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("productParameterSpecification")
public class ProductParameterSpecificationController extends BaseController<ProductParameterSpecificationEntity, ProductParameterSpecificationInfo> {
    @Resource
    private ProductParameterSpecificationService productParameterSpecificationService;

    @Override
    protected com.ecms.service.BaseService<ProductParameterSpecificationEntity, ProductParameterSpecificationInfo> getBaseService() {
        return productParameterSpecificationService;
    }

    @RequestMapping("save")
    public Result<Void> save(@RequestBody ProductParameterSpecificationParam param) {
        productParameterSpecificationService.save(param);
        return ResultUtils.success();
    }

    @RequestMapping("search")
    public Result<Pager<ProductParameterSpecificationInfo>> listByLike(int pageNum, int pageSize, ProductParameterSpecificationInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<ProductParameterSpecificationInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, true);
        return ResultUtils.success(pager);
    }

    @RequestMapping("/status/update")
    public Result<Void> updateStatus(int id) {
        productParameterSpecificationService.updateStatus(id);
        return ResultUtils.success();
    }

    @RequestMapping("/searchList")
    public Result<List<ProductParameterSpecificationInfo>> searchList(){
        ProductParameterSpecificationInfo productParameterSpecificationInfo = new ProductParameterSpecificationInfo();
        productParameterSpecificationInfo.setStatus(1);
        List<ProductParameterSpecificationInfo> list = productParameterSpecificationService.getListBy(productParameterSpecificationInfo);
        return ResultUtils.success(list);
    }
}