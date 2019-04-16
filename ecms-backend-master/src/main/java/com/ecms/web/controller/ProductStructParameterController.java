package com.ecms.web.controller;

import com.ecms.bean.ProductStructParameterInfo;
import com.ecms.dal.entity.ProductStructParameterEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductStructParameterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/productStructParameter")
public class ProductStructParameterController extends BaseController<ProductStructParameterEntity, ProductStructParameterInfo> {

    @Resource
    private ProductStructParameterService service;
    @Override
    protected BaseService<ProductStructParameterEntity, ProductStructParameterInfo> getBaseService() {
        return service;
    }
}
