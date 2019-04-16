package com.ecms.web.controller;

import com.ecms.bean.EquipmentManageInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.EquipmentManageEntity;
import com.ecms.service.BaseService;
import com.ecms.service.EquipmentManageService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("equipmentManage")
public class EquipmentManageController extends BaseController<EquipmentManageEntity,EquipmentManageInfo> {

    @Resource
    private EquipmentManageService equipmentManageService;

    @Override
    protected BaseService<EquipmentManageEntity,EquipmentManageInfo> getBaseService() {
        return equipmentManageService;
    }

    @RequestMapping("equiList")
    public Result<Pager<EquipmentManageInfo>> getEquipmentData(int pageNum, EquipmentManageInfo equipmentManageInfo) {
        ExchangeUtils.convertBlank2Null(equipmentManageInfo);
        Pager<EquipmentManageInfo> pager = equipmentManageService.getModelList(pageNum, DEFAULT_PAGE_SIZE, equipmentManageInfo);
        return ResultUtils.success(pager);
    }
    
    /**产品管理中的设备程序中新增设备程序用到的获取不重复设备名称
     * @param pageNum
     * @param equipmentManageInfo
     * @return
     */
    @RequestMapping("getUniqueEquipment")
    public Result<Pager<EquipmentManageInfo>> getUniqueEquipment(int pageNum, EquipmentManageInfo equipmentManageInfo) {
        Pager<EquipmentManageInfo> pager = equipmentManageService.getUniqueName(pageNum, DEFAULT_PAGE_SIZE, equipmentManageInfo);
        return ResultUtils.success(pager);
    }

    @RequestMapping("equiInsert")
    public Result<Void> insertUser(EquipmentManageInfo equipmentManageInfo) {
        equipmentManageService.insertEquipment(equipmentManageInfo);
        return ResultUtils.success();
    }

    @RequestMapping("equiUpdate")
    public Result<Void> updateUser(EquipmentManageInfo equipmentManageInfo) {
        equipmentManageService.updateUser(equipmentManageInfo);
        return ResultUtils.success();
    }

    @RequestMapping("getMaxEquipmentList")
    public Result<Integer> getMaxEquipmentList(int pageNum, EquipmentManageInfo equipmentManageInfo) {
        ExchangeUtils.convertBlank2Null(equipmentManageInfo);
        int maxEquipmentNumber = equipmentManageService.getMaxEquipmentNumber(pageNum, DEFAULT_PAGE_SIZE, equipmentManageInfo);
        //查询设备编号最大值再+1，用于下一条的设备编号
        return ResultUtils.success(maxEquipmentNumber+1);
    }

    /*@RequestMapping("getUserList")
    public Result<Pager<EquipmentUserInfo>> getUserList(int pageNum, EquipmentUserInfo equipmentUserInfo) {
        ExchangeUtils.convertBlank2Null(equipmentUserInfo);
        ///todo
        equipmentUserInfo.setEquipmentNumber(22);
        Pager<EquipmentUserInfo> pager = equipmentManageService.getUserList(pageNum, DEFAULT_PAGE_SIZE, equipmentUserInfo);
        System.err.println(pager.getList().get(0).getEmployeeName());
        return ResultUtils.success(pager);
    }*/
    @RequestMapping("searchList")
    public Result<List<EquipmentManageInfo>> searchList(){
        List<EquipmentManageInfo> list = equipmentManageService.getValidList();
        return ResultUtils.success(list);
    }
}
