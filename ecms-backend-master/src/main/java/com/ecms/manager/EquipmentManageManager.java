package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.EquipmentManageEntity;
import com.ecms.dal.mapper.EquipmentManageMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EquipmentManageManager extends BaseManager<EquipmentManageEntity> {

    @Resource
    private EquipmentManageMapper equipmentManageMapper;

    @Override
    protected BasicMapper<EquipmentManageEntity> getMapper() {
        return equipmentManageMapper;
    }

    public EquipmentManageEntity getEquimentByAccount(String account) {
        return equipmentManageMapper.selectByAccount(account);
    }

    public List<EquipmentManageEntity> getEquipmentList(int pageNum, int pageSize, EquipmentManageEntity equipmentManageEntity) {
        PageHelper.startPage(pageNum, pageSize);
        return equipmentManageMapper.select(equipmentManageEntity);
    }

    public long getTotal(EquipmentManageEntity equipmentManageEntity) {
        return equipmentManageMapper.selectCount(equipmentManageEntity);
    }

    public void insertEquipment(EquipmentManageEntity equipmentManageEntity) {
        equipmentManageMapper.insertSelective(equipmentManageEntity);
    }

    public int getMaxEquipmentNumber(int pageNum, int pageSize, EquipmentManageEntity equipmentManageEntity) {
        PageHelper.startPage(1, 1);
        return equipmentManageMapper.getMaxEquipmentNumber(equipmentManageEntity);
    }

	public List<String> getUniqueName() {
		return equipmentManageMapper.getUniqueName();
	}
}
