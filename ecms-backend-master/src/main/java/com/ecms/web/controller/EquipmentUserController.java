package com.ecms.web.controller;

import com.ecms.bean.EquipmentUserInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.EquipmentUserEntity;
import com.ecms.service.BaseService;
import com.ecms.service.EquipmentUserService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("equipmentUser")
public class EquipmentUserController extends BaseController<EquipmentUserEntity,EquipmentUserInfo> {

    @Resource
    private EquipmentUserService equipmentUserService;

    @Override
    protected BaseService<EquipmentUserEntity,EquipmentUserInfo> getBaseService() {
        return equipmentUserService;
    }

    @RequestMapping("equiUserList")
    public Result<Pager<EquipmentUserInfo>> getEquipmentData(int pageNum, EquipmentUserInfo equipmentUserInfo) {
        ExchangeUtils.convertBlank2Null(equipmentUserInfo);
        Pager<EquipmentUserInfo> pager = equipmentUserService.getUserModelList(pageNum, DEFAULT_PAGE_SIZE, equipmentUserInfo);
        return ResultUtils.success(pager);
    }

    @RequestMapping("updateEquimentUser")
    public Result<Void> updateEquimentUser(EquipmentUserInfo equipmentUserInfo) {
        equipmentUserService.updateEquimentUser(equipmentUserInfo);
        return ResultUtils.success();
    }

    @RequestMapping("equimentList")
    public Result<List<Map<String,Object>>> getEquimentList(EquipmentUserInfo equipmentUserInfo) {
        ExchangeUtils.convertBlank2Null(equipmentUserInfo);
        String employeeId = equipmentUserInfo.getEmployeeId();
        List<Map<String,Object>> list = equipmentUserService.getListByEmployeeId(employeeId);
        return ResultUtils.success(list);
    }

    @RequestMapping("deleteEquipment")
    public Result<Void> deleteEquipment(EquipmentUserInfo equipmentUserInfo){
        equipmentUserService.deleteBy(equipmentUserInfo.getId());
        return ResultUtils.success();
    }

    @RequestMapping("getUserList")
    public Result<List<EquipmentUserInfo>> getUserList(EquipmentUserInfo equipmentUserInfo){
        equipmentUserInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<EquipmentUserInfo> euiList = equipmentUserService.getListBy(equipmentUserInfo);
        return ResultUtils.success(euiList);
    }
}
