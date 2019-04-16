package com.ecms.web.controller;

import com.ecms.bean.UnqualifiedTypeInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.UnqualifiedTypeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.UnqualifiedTypeService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_INVALID;

/**
 * @author Richie
 * @version 2018/12/19 16:48
 */
@RestController
@RequestMapping("unqualifiedType")
public class UnqualifiedTypeController extends BaseController<UnqualifiedTypeEntity,UnqualifiedTypeInfo> {

    @Resource
    private UnqualifiedTypeService unqualifiedTypeService;

    @Override
    protected BaseService<UnqualifiedTypeEntity, UnqualifiedTypeInfo> getBaseService() {
        return unqualifiedTypeService;
    }

    @RequestMapping("insert")
    public Result<Void> insertBy(UnqualifiedTypeInfo unqualifiedTypeInfo) {
        unqualifiedTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<UnqualifiedTypeInfo> utiList = unqualifiedTypeService.getListBy(unqualifiedTypeInfo);
        if(utiList.size() != 0){
            return ResultUtils.failure("类型重复");
        }else{
            unqualifiedTypeService.insertBy(unqualifiedTypeInfo);
            return ResultUtils.success();
        }
    }

    @RequestMapping("update")
    public Result<Void> updateByPrimaryKey(UnqualifiedTypeInfo unqualifiedTypeInfo) {
        unqualifiedTypeInfo.setStatus(STATUS_INVALID);
        unqualifiedTypeService.updateByPrimaryKeySelective(unqualifiedTypeInfo);
        return ResultUtils.success();
    }

    @RequestMapping("/searchList")
    public Result<List<UnqualifiedTypeInfo>> searchList(){
        UnqualifiedTypeInfo uti = new UnqualifiedTypeInfo();
        uti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        uti.setStatus(1);
        List<UnqualifiedTypeInfo> utiList = unqualifiedTypeService.getListBy(uti);
        return ResultUtils.success(utiList);
    }
}
