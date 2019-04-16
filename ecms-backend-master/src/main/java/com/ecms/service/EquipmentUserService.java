package com.ecms.service;

import com.ecms.bean.EquipmentUserInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.EquipmentUserEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.EquipmentUserManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentUserService extends BaseService<EquipmentUserEntity, EquipmentUserInfo> {

	@Resource
	private EquipmentUserManager equipmentUserManager;

    @Override
    protected BaseManager<EquipmentUserEntity> getManager() {
        return equipmentUserManager;
    }

    public Pager<EquipmentUserInfo> getUserModelList(int pageNum, int pageSize, EquipmentUserInfo equipmentUserInfo){
        EquipmentUserEntity equipmentUserEntity = ExchangeUtils.exchangeObject(equipmentUserInfo, EquipmentUserEntity.class);
        List<EquipmentUserEntity> list = equipmentUserManager.getUserModelList(pageNum, pageSize, equipmentUserEntity);
        long total = equipmentUserManager.getTotal(equipmentUserEntity);
        List<EquipmentUserInfo> equipmentUserList = ExchangeUtils.exchangeList(list, EquipmentUserInfo.class);
        Pager<EquipmentUserInfo> pager = new Pager<EquipmentUserInfo>(pageNum, pageSize, total, equipmentUserList);
        return pager;
    }

    public void updateEquimentUser(EquipmentUserInfo equipmentUserInfo) {
        EquipmentUserEntity equipmentUserEntity = ExchangeUtils.exchangeObject(equipmentUserInfo, EquipmentUserEntity.class);
        if(equipmentUserInfo.getTableDataList() != null){
            for (int i = 0; i<equipmentUserInfo.getTableDataList().size();i++){
                String equipmentNumber = equipmentUserInfo.getTableDataList().get(i).get("equipmentNumber").toString();
                for(int j = 0;j < equipmentUserInfo.getEquipmentList().size();j++){
                    if(equipmentNumber.equals(equipmentUserInfo.getEquipmentList().get(j).get("equipmentNumber"))){
                        equipmentUserInfo.getEquipmentList().remove(j);
                    }
                }
            }
        }
        List<Map<String,Object>> listTemp = equipmentUserInfo.getEquipmentList();
        for(int i=0;i<listTemp.size();i++){
            EquipmentUserEntity eue = new EquipmentUserEntity();
            eue.setEquipmentNumber(Integer.parseInt(listTemp.get(i).get("equipmentNumber").toString()));
            // 表中不存在该字段
            /*eue.setPostId(null);
            eue.setDepartmentId(null);*/
            ///todo 公司Id
            eue.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            eue.setEquipmentName(listTemp.get(i).get("equipmentName").toString());
            eue.setEmployeeId(equipmentUserEntity.getEmployeeId());
            eue.setEmployeeName(equipmentUserEntity.getEmployeeName());
            eue.setStatus(STATUS_DEFAULT);
            equipmentUserManager.insertBy(eue);
        }
    }

    public List<Map<String,Object>> getListByEmployeeId(String employeeId){
        List<Map<String,Object>> list = equipmentUserManager.getListByEmployeeId(employeeId);
        return list;
    }
}
