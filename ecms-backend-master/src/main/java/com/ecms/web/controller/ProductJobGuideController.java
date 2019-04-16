package com.ecms.web.controller;

import com.ecms.bean.ProductJobGuideInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductJobGuideEntity;
import com.ecms.service.ProductJobGuideService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("productJobGuide")
public class ProductJobGuideController extends BaseController<ProductJobGuideEntity, ProductJobGuideInfo> {
    @Resource
    private ProductJobGuideService productJobGuideService;

    @Override
    protected com.ecms.service.BaseService<ProductJobGuideEntity, ProductJobGuideInfo> getBaseService() {
        return productJobGuideService;
    }

    @RequestMapping("save")
    public Result<Void> save(ProductJobGuideInfo info) {
        productJobGuideService.save(info);
        return ResultUtils.success();
    }

    @RequestMapping("remove")
    public Result<Void> remove(int id) {
        productJobGuideService.remove(id);
        return ResultUtils.success();
    }

    @RequestMapping("searchList")
    public Result<List<ProductJobGuideInfo>> searchList(){
        List<ProductJobGuideInfo> list = productJobGuideService.getValidList();
        return ResultUtils.success(list);
    }

}