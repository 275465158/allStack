package com.ecms.web.controller;

import com.ecms.bean.ProductStructOptionInfo;
import com.ecms.dal.entity.ProductStructOptionEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductStructOptionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("ProductStructOption")
public class ProductStructOptionController extends BaseController<ProductStructOptionEntity, ProductStructOptionInfo> {
    @Resource
    private ProductStructOptionService service;
    @Override
    protected BaseService<ProductStructOptionEntity, ProductStructOptionInfo> getBaseService() {
        return service;
    }
}
