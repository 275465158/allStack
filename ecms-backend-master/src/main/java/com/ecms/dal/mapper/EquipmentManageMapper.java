package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.EquipmentManageEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.javassist.util.proxy.ProxyFactory.UniqueName;

import java.util.List;

public interface EquipmentManageMapper extends BasicMapper<EquipmentManageEntity>{
        @Select("select * from equipment_manage")
        @Results(id = "equipmentMap", value = {
                @Result(column = "equipment_number", property = "equipmentNumber"),
                @Result(column = "equipment_name", property = "equipmentName"),
                @Result(column = "equipment_model", property = "equipmentModel"),
                @Result(column = "vender", property = "vender"),
                @Result(column = "dept_id", property = "deptId"),
                @Result(column = "equipment_status", property = "equipmentStatus")
        })
        List<EquipmentManageEntity> tt();
        
        @Select("SELECT DISTINCT equipment_name from equipment_manage")
        @Results(id = "equipmentUniqueNameMap", value = {
        		@Result(column = "equipment_name", property = "equipmentName")
        })
        List<String> getUniqueName();

        @Select("select * from equipment_manage")
        @ResultMap("equipmentMap")
        EquipmentManageEntity selectByAccount(String account);

        @Select("SELECT ifnull(MAX(equipment_number),0) AS equipmentNumber FROM equipment_manage")
        @Results(id = "equipmentNumber")
        int getMaxEquipmentNumber(EquipmentManageEntity equipmentManageEntity);
}
