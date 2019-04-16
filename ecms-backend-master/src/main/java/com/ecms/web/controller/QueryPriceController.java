package com.ecms.web.controller;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.QueryPriceInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.QueryPriceEntity;
import com.ecms.service.BaseService;
import com.ecms.service.DeliveryAddressService;
import com.ecms.service.QueryPriceService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("queryPrice")
public class QueryPriceController extends BaseController<QueryPriceEntity, QueryPriceInfo> {
    @Resource
    private QueryPriceService service;
    @Resource
    private DeliveryAddressService deliveryAddressService;


    @Override
    protected BaseService<QueryPriceEntity, QueryPriceInfo> getBaseService() {
        return service;
    }
    @RequestMapping("/searchProductList")
    public Result<Pager<MaterialBomInfo>> searchMaterialList(int pageNum, int pageSize, MaterialBomInfo materialBomInfo) {
        return ResultUtils.success(service.searchMaterialList( pageNum,  pageSize,  materialBomInfo));
    }
    @RequestMapping("/saveOrUpdate")
    public Result<QueryPriceInfo> saveOrUpdate(QueryPriceInfo info) {
        service.saveOrUpdate(info);
        return ResultUtils.success();
    }
    @RequestMapping("getById")
    public Result<QueryPriceInfo> getById(Integer id){
        QueryPriceInfo info=getBaseService().getModel(id);
        String detialAddress = deliveryAddressService.getModel(service.getModel(id).getAddressId()).getProvince() + deliveryAddressService.getModel(service.getModel(id).getAddressId()).getCity() + deliveryAddressService.getModel(service.getModel(id).getAddressId()).getCounty();
        info.setDetailAddress(detialAddress);
        return ResultUtils.success(info);
    }

    @RequestMapping("list")
    public Result<Pager<QueryPriceInfo>> list(int pageNum, QueryPriceInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        if(LoginFilter.getCurrentUser().getCustomerId()!=null){
            info.setCustomerId(LoginFilter.getCurrentUser().getCustomerId());
        }else {
            info.setCustomerId(0);
        }
        Pager<QueryPriceInfo> pager = getBaseService().getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
        return ResultUtils.success(pager);
    }
    public String getNextId(){
        return 	service.getNextId();
    }


    }

