package com.ecms.web.controller;

import com.ecms.bean.MaterialRepertoryOutboundlogInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.dal.entity.MaterialRepertoryOutboundlogEntity;
import com.ecms.service.BaseService;
import com.ecms.service.EmployeeService;
import com.ecms.service.MaterialRepertoryOutboundlogService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Richie
 * @version 2018/11/5 14:24
 */
@RestController
@RequestMapping("materialRepertoryOutboundlog")
public class MaterialRepertoryOutboundlogController extends BaseController<MaterialRepertoryOutboundlogEntity,MaterialRepertoryOutboundlogInfo> {

    @Resource
    private MaterialRepertoryOutboundlogService materialRepertoryOutboundlogService;

    @Resource
    private EmployeeService employeeService;

    @Override
    protected BaseService<MaterialRepertoryOutboundlogEntity, MaterialRepertoryOutboundlogInfo> getBaseService() {
        return materialRepertoryOutboundlogService;
    }

    /**
     * 获取出库记录
     * @param pageNum
     * @param pageSize
     * @param info
     * @return
     */
    @RequestMapping("like")
    public Result<Pager<MaterialRepertoryOutboundlogInfo>> listByLike(int pageNum, int pageSize, MaterialRepertoryOutboundlogInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<MaterialRepertoryOutboundlogInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, false);
        List<MaterialRepertoryOutboundlogInfo> mroliList = pager.getList();
        if(mroliList.size() != 0){
            for(MaterialRepertoryOutboundlogInfo mroli : mroliList){
                EmployeeEntity ee = employeeService.getEntity(mroli.getTakeMaterialId());
                if(ee != null){
                    mroli.setTakeMaterialName(ee.getName());
                }
            }
        }
        return ResultUtils.success(pager);
    }
}
