package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.EquipmentUserEntity;
import com.ecms.dal.mapper.EquipmentUserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class EquipmentUserManager extends BaseManager<EquipmentUserEntity>{

    @Resource
    private EquipmentUserMapper equipmentUserMapper;

    @Override
    protected BasicMapper<EquipmentUserEntity> getMapper() {
        return equipmentUserMapper;
    }

    public EquipmentUserEntity getEquimentByAccount(String account) {
        return equipmentUserMapper.selectByAccount(account);
    }

    public long getTotal(EquipmentUserEntity equipmentUserEntity) {
        return equipmentUserMapper.selectCount(equipmentUserEntity);
    }

    public void insertEquipmentUser(EquipmentUserEntity equipmentUserEntity) {
        equipmentUserMapper.insertSelective(equipmentUserEntity);
    }

    public List<EquipmentUserEntity> getUserModelList(int pageNum, int pageSize,EquipmentUserEntity equipmentUserEntity){
        PageHelper.startPage(pageNum, pageSize);
        return equipmentUserMapper.getUserModelList(equipmentUserEntity);
    }
    public List<Map<String,Object>> getListByEmployeeId(String employeeId){
        return equipmentUserMapper.getListByEmployeeId(employeeId);
    }

    public void deleteByEquimentNumber(Integer equipmentNumber){
        equipmentUserMapper.deleteByEquimentNumber(equipmentNumber);
    }
}
