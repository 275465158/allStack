package com.ecms.web.controller;

import com.ecms.bean.ProductStructRelationInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductStructRelationEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductStructRelationService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("ProductStructRelation")
public class ProductStructRelationController extends BaseController<ProductStructRelationEntity, ProductStructRelationInfo> {
    @Resource
    private ProductStructRelationService service;
    @Override
    protected BaseService<ProductStructRelationEntity, ProductStructRelationInfo> getBaseService() {
        return service;
    }

    @RequestMapping("/searchList")
    public Result<List<ProductStructRelationInfo>> searchList(Integer optionId){
        List<ProductStructRelationInfo> list = service.searchList(optionId);
        return ResultUtils.success(list);
    }

    @RequestMapping("/deleteByOptionSelectId")
    public Result<Void> deleteByOptionIdAndSelectId(Integer optionId, Integer selectorId){
        ProductStructRelationInfo searchInfo = new ProductStructRelationInfo();
        searchInfo.setOptionId(optionId);
        searchInfo.setSelectId(selectorId);
        List<ProductStructRelationInfo> list = service.getListBy(searchInfo);
        for(int i = 0; i < list.size(); i++){
            service.deleteBy(list.get(i).getId());
        }
        return ResultUtils.success();
    }
}
