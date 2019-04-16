package com.ecms.web.controller;

import com.ecms.annotation.SaveLog;
import com.ecms.bean.ProductDrawingInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductDrawingEntity;
import com.ecms.service.ProductDrawingService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("productDrawing")
public class ProductDrawingController extends BaseController<ProductDrawingEntity, ProductDrawingInfo> {

    @Resource
    private ProductDrawingService productDrawingService;

    @Override
    protected com.ecms.service.BaseService<ProductDrawingEntity, ProductDrawingInfo> getBaseService() {
        return productDrawingService;
    }

    @RequestMapping("save")
    public Result<Void> save(ProductDrawingInfo info) {
        productDrawingService.save(info);
        return ResultUtils.success();
    }

    @RequestMapping("saveDrawing")
    public Result<Void> saveDrawing(ProductDrawingInfo info) {
        String drawingCode = info.getDrawingCode();
        ProductDrawingInfo pdi = new ProductDrawingInfo();
        pdi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        pdi.setDrawingCode(drawingCode);
        List<ProductDrawingInfo> pdiList = productDrawingService.getListBy(pdi);
        if(pdiList.size() != 0 && !pdiList.get(0).getId().equals(info.getId())){
            return ResultUtils.failure("已存在相同图纸，图纸图号不能相同");
        }else{
            productDrawingService.save(info);
            return ResultUtils.success();
        }
    }

    @SaveLog(name = "删除图纸")
    @RequestMapping("remove")
    public Result<Void> remove(int id) {
        productDrawingService.remove(id);
        return ResultUtils.success();
    }

    @RequestMapping("searchDrawingList")
    public Result<List<ProductDrawingInfo>> searchDrawingList(){
        ProductDrawingInfo drawingInfo = new ProductDrawingInfo();
        drawingInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        drawingInfo.setStatus(STATUS_DEFAULT);
        List<ProductDrawingInfo> list = productDrawingService.getListBy(drawingInfo);
        return ResultUtils.success(list);
    } /** 
      * @Author:pratice    
      * @date 2019/1/9 
      * @Description: 
      */  
    @RequestMapping("searchDrawingPager")
    public Result<Pager<ProductDrawingInfo>> searchDrawingPager(int pageNum, int pageSize,ProductDrawingInfo info){
        ExchangeUtils.convertBlank2Null(info);
        Pager<ProductDrawingInfo> pager = productDrawingService.getModelListByLike(pageNum,pageSize,info,false);
        return ResultUtils.success(pager);
    }

}