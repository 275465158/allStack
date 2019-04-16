package com.ecms.service;

import com.ecms.bean.EquipmentManageInfo;
import com.ecms.bean.EquipmentUserInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.EquipmentManageEntity;
import com.ecms.dal.entity.EquipmentUserEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.EquipmentManageManager;
import com.ecms.manager.EquipmentUserManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentManageService extends BaseService<EquipmentManageEntity,EquipmentManageInfo> {

    @Resource
    private EquipmentManageManager equipmentManageManager;
    @Resource
    private EquipmentUserManager equipmentUserManager;

    @Override
    protected BaseManager<EquipmentManageEntity> getManager() {
        return equipmentManageManager;
    }

    public Pager<EquipmentManageInfo> getEquipmentList(int pageNum, int pageSize, EquipmentManageInfo equipmentManageInfo) {
        EquipmentManageEntity equipmentManageEntity = ExchangeUtils.exchangeObject(equipmentManageInfo, EquipmentManageEntity.class);
        List<EquipmentManageEntity> list = equipmentManageManager.getEquipmentList(pageNum, pageSize, equipmentManageEntity);
        long total = equipmentManageManager.getTotal(equipmentManageEntity);
        List<EquipmentManageInfo> equipmentList = ExchangeUtils.exchangeList(list, EquipmentManageInfo.class);
        Pager<EquipmentManageInfo> pager = new Pager<EquipmentManageInfo>(pageNum, pageSize, total, equipmentList);
        return pager;
    }
    

    @Transactional
    public void insertEquipment(EquipmentManageInfo equipmentManageInfo) {
        EquipmentManageEntity equipmentManageEntity = ExchangeUtils.exchangeObject(equipmentManageInfo, EquipmentManageEntity.class);
        if(equipmentManageInfo.getUserList().size() != 0){
            EquipmentUserInfo equipmentUserInfo = new EquipmentUserInfo();
            String employeeId;
            Integer equipmentNumber = equipmentManageInfo.getEquipmentNumber();
            String equipmentName = equipmentManageInfo.getEquipmentName();
            String employeeName;
            List<Map<String,Object>> userList = equipmentManageInfo.getUserList();
            for(Map<String,Object> res : userList){
                equipmentUserInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                employeeId = res.get("id").toString();
                employeeName = res.get("name").toString();
                equipmentUserInfo.setEmployeeId(employeeId);
                equipmentUserInfo.setStatus(STATUS_DEFAULT);
                equipmentUserInfo.setEmployeeName(employeeName);
                equipmentUserInfo.setEquipmentNumber(equipmentNumber);
                equipmentUserInfo.setEquipmentName(equipmentName);
                EquipmentUserEntity equipmentUserEntity = ExchangeUtils.exchangeObject(equipmentUserInfo,EquipmentUserEntity.class);
                equipmentUserManager.insertBy(equipmentUserEntity);
            }
            equipmentManageEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            equipmentManageManager.insertBy(equipmentManageEntity);
        }else{
            throw new IllegalArgumentException("保存失败！未选择人员列表");
        }
    }

    public void updateUser(EquipmentManageInfo equipmentManageInfo) {
        EquipmentManageEntity equipmentManageEntity = ExchangeUtils.exchangeObject(equipmentManageInfo, EquipmentManageEntity.class);
        if(equipmentManageInfo.getEquipmentNumber() != null && equipmentManageInfo.getUserList().size() != 0){
            equipmentUserManager.deleteByEquimentNumber(equipmentManageInfo.getEquipmentNumber());
            EquipmentUserInfo equipmentUserInfo = new EquipmentUserInfo();
            String employeeId;
            Integer equipmentNumber = equipmentManageInfo.getEquipmentNumber();
            String equipmentName = equipmentManageInfo.getEquipmentName();
            String employeeName;
            List<Map<String,Object>> userList = equipmentManageInfo.getUserList();
            for(Map<String,Object> res : userList){
                equipmentUserInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                employeeId = res.get("id").toString();
                employeeName = res.get("name").toString();
                equipmentUserInfo.setEmployeeId(employeeId);
                equipmentUserInfo.setStatus(STATUS_DEFAULT);
                equipmentUserInfo.setEmployeeName(employeeName);
                equipmentUserInfo.setEquipmentNumber(equipmentNumber);
                equipmentUserInfo.setEquipmentName(equipmentName);
                EquipmentUserEntity equipmentUserEntity = ExchangeUtils.exchangeObject(equipmentUserInfo,EquipmentUserEntity.class);
                equipmentUserManager.insertBy(equipmentUserEntity);
            }
        }
    }

    public int getMaxEquipmentNumber(int pageNum, int pageSize, EquipmentManageInfo equipmentManageInfo) {
        EquipmentManageEntity equipmentManageEntity = ExchangeUtils.exchangeObject(equipmentManageInfo, EquipmentManageEntity.class);
        int maxEquipmentNumber  = equipmentManageManager.getMaxEquipmentNumber(pageNum, pageSize, equipmentManageEntity);
        return maxEquipmentNumber;
    }

    public Pager<EquipmentUserInfo> getUserList(int pageNum, int pageSize, EquipmentUserInfo equipmentUserInfo) {
        EquipmentUserEntity equipmentUserEntity = ExchangeUtils.exchangeObject(equipmentUserInfo, EquipmentUserEntity.class);
        List<EquipmentUserEntity> list = equipmentUserManager.getListBy(equipmentUserEntity);
        long total = equipmentUserManager.getTotal(equipmentUserEntity);
        List<EquipmentUserInfo> equipmentUserList = ExchangeUtils.exchangeList(list, EquipmentUserInfo.class);
        Pager<EquipmentUserInfo> pager = new Pager<EquipmentUserInfo>(pageNum, pageSize, total, equipmentUserList);
        return pager;
    }

	public Pager<EquipmentManageInfo> getUniqueName(int pageNum, int pageSize, EquipmentManageInfo equipmentManageInfo) {
		EquipmentManageEntity equipmentManageEntity=ExchangeUtils.exchangeObject(equipmentManageInfo,EquipmentManageEntity.class);
		List<String> list = equipmentManageManager.getUniqueName();
		long total = equipmentManageManager.getTotal(equipmentManageEntity);
		ExchangeUtils.exchangeList(list, EquipmentManageInfo.class);
		Pager<EquipmentManageInfo> pager = new Pager<EquipmentManageInfo>(pageNum, pageSize, total, list);
		return pager;
	}

}
