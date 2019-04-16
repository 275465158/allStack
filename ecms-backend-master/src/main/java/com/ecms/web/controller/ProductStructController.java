package com.ecms.web.controller;

import com.ecms.bean.ProductStructInfo;
import com.ecms.bean.ProductStructParameterInfo;
import com.ecms.bean.ProductTypeInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductStructEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("productStruct")
public class ProductStructController extends BaseController<ProductStructEntity, ProductStructInfo> {
    @Resource
    private ProductStructService service;
    @Resource
    private ProductStructSelectorService selectorService;
    @Resource
    private ProductStructParameterService parameterService;
    @Resource
    private ProductTypeService typeService;

    @Override
    protected BaseService<ProductStructEntity, ProductStructInfo> getBaseService() {
        return service;
    }

    /**
     * 查询产品结构列表
     * @param pageNum
     * @param pageSize
     * @param structInfo
     * @return
     */
    @RequestMapping("/searchStructPage")
    public Result<Pager<ProductStructInfo>> searchStructPage(int pageNum, int pageSize, ProductStructInfo structInfo){
        ExchangeUtils.convertBlank2Null(structInfo);
        structInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        Pager<ProductStructInfo> pager = service.getModelListByLike(pageNum, pageSize, structInfo, true);
        for(int i = 0; i < pager.getList().size(); i++){
            ProductStructParameterInfo searchSelect = new ProductStructParameterInfo();
            searchSelect.setStructId(pager.getList().get(i).getId());
            List<ProductStructParameterInfo> params = parameterService.getListBy(searchSelect);
            pager.getList().get(i).setParameters(params);
            if(pager.getList().get(i).getProductTypeId()!=null) {
                ProductTypeInfo typeInfo = typeService.getModel(pager.getList().get(i).getProductTypeId());
                if (typeInfo != null) {
                    pager.getList().get(i).setProductTypeInfo(typeInfo);
                }
            }
        }
        return ResultUtils.success(pager);
    }

    @RequestMapping("/updateStatus")
    public Result<Void> updateStatus(Integer id){
        service.updateStatus(id);
        return ResultUtils.success();
    }

    @RequestMapping("/saveStruct")
    public Result<Void> saveProductStruct(ProductStructInfo structInfo){
        structInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<ProductStructInfo> existList = service.getListBy(structInfo);
        if(existList.size()!=0){
            return ResultUtils.failure("结构名称已存在");
        }else{
            service.saveStruct(structInfo);
        }
        return ResultUtils.success();
    }

    @RequestMapping("/modifyStruct")
    public Result<Void> modifyProductStruct(ProductStructInfo structInfo){
        service.modifyStruct(structInfo);
        return ResultUtils.success();
    }

    @RequestMapping("/searchList")
    public Result<List<ProductStructInfo>> searchList(){
        List<ProductStructInfo> list = service.getValidList();
        return ResultUtils.success(list);
    }
}
