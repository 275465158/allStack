package com.ecms.web.controller;

import com.ecms.bean.ProductStructOptionInfo;
import com.ecms.bean.ProductStructSelectorInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductStructSelectorEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductStructOptionService;
import com.ecms.service.ProductStructSelectorService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("ProductStructSelector")
public class ProductStructSelectorController extends BaseController<ProductStructSelectorEntity, ProductStructSelectorInfo> {
    @Resource
    private ProductStructSelectorService service;
    @Resource
    private ProductStructOptionService optionService;
    @Override
    protected BaseService<ProductStructSelectorEntity, ProductStructSelectorInfo> getBaseService() {
        return service;
    }

    /**
     * 查询产品结构
     * @param pageNum
     * @param pageSize
     * @param selectorInfo
     * @return
     */
    @RequestMapping("/searchSelectorPage")
    public Result<Pager<ProductStructSelectorInfo>> searchSelectorPage(int pageNum, int pageSize, ProductStructSelectorInfo selectorInfo){
        ExchangeUtils.convertBlank2Null(selectorInfo);
        selectorInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        Pager<ProductStructSelectorInfo> pager = service.getModelListByLike(pageNum, pageSize, selectorInfo, true);
        if (pager.getList().size() != 0) {
            for(int i = 0; i < pager.getList().size(); i++){
                ProductStructOptionInfo searchOption = new ProductStructOptionInfo();
                searchOption.setSelectorId(pager.getList().get(i).getId());
                List<ProductStructOptionInfo> optionList = optionService.getListBy(searchOption);
                for(int j = 0; j < optionList.size(); j++){
                    optionList.get(j).setType("option");
                }
                pager.getList().get(i).setOptions(optionList);
            }
        }
        return ResultUtils.success(pager);
    }

    /**
     * 修改结构体是否生效
     * @param id
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result<Void> updateStatus(Integer id){
        service.updateStatus(id);
        return ResultUtils.success();
    }

    @RequestMapping("/saveStructSelector")
    public Result<Void> saveProductStructSelector(ProductStructSelectorInfo selectorInfo){
        selectorInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<ProductStructSelectorInfo> selectorList = service.getListBy(selectorInfo);
        if(selectorList.size() != 0){
            return ResultUtils.failure("结构名称已存在");
        }else{
            service.saveSelector(selectorInfo);
        }
        return ResultUtils.success();
    }

    @RequestMapping("/modifyStructSelector")
    public Result<Void> modifyProductStructSelector(ProductStructSelectorInfo selectorInfo){
        service.modifySelector(selectorInfo);
        return ResultUtils.success();
    }

    @RequestMapping("/searchList")
    public Result<List<ProductStructSelectorInfo>> searchList(){
        List<ProductStructSelectorInfo> list = service.getValidList();
        return ResultUtils.success(list);
    }

    @RequestMapping("/excludeSearch")
    public Result<List<ProductStructSelectorInfo>> excludeSearch(Integer id){
        List<ProductStructSelectorInfo> list = service.getListExclude(id);
        return ResultUtils.success(list);
    }
}
