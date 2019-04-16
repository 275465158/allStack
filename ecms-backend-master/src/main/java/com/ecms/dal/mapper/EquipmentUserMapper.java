package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.EquipmentUserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface EquipmentUserMapper extends BasicMapper<EquipmentUserEntity>{
        @Select("select * from equipment_user")
        @Results(id = "equiupmentUserMap", value = {
                @Result(column = "equipment_number", property = "equipment_number"),
                @Result(column = "employee_id", property = "employeeId"),
                @Result(column = "employee_name", property = "employeeName"),
                @Result(column = "company_id", property = "companyId")
        })
        List<EquipmentUserEntity> tt();

        @Select("select * from equipment_user")
        @ResultMap("equiupmentUserMap")
        EquipmentUserEntity selectByAccount(String account);

        @Select("SELECT a.id AS id,a.equipment_number AS equipmentNumber," +
                "a.employee_id AS employeeId,a.employee_name AS employeeName," +
                "a.company_id AS companyId,b.department_id AS departmentId,b.post_id AS postId FROM equipment_user a " +
                "LEFT JOIN employee b ON a.employee_id = b.id")
        @Results(id = "getUserModelList", value = {
                @Result(column = "equipment_number", property = "equipment_number"),
                @Result(column = "employee_id", property = "employee_id"),
                @Result(column = "employee_name", property = "employee_name"),
                @Result(column = "company_id", property = "company_id"),
                @Result(column = "department_id", property = "department_id"),
                @Result(column = "post_id", property = "post_id"),
                @Result(column = "id", property = "id"),
        })
        List<EquipmentUserEntity> getUserModelList(EquipmentUserEntity equipmentUserEntity);

        @Select("SELECT a.id AS id,a.equipment_number AS equipmentNumber," +
                "a.employee_id AS employeeId,a.employee_name AS employeeName,a.equipment_name AS equipmentName," +
                "a.company_id AS companyId,a.equipment_name AS equipmentName FROM equipment_user a WHERE a.employee_id = #{employeeId}")
        @Results(id = "getListByEmployeeId", value = {
                @Result(column = "equipment_number", property = "equipment_number"),
                @Result(column = "equipment_name", property = "equipment_name"),
                @Result(column = "employee_id", property = "employee_id"),
                @Result(column = "employee_name", property = "employee_name"),
                @Result(column = "company_id", property = "company_id"),
                @Result(column = "equipment_name", property = "equipment_name"),
                @Result(column = "id", property = "id")
        })
        List<Map<String,Object>> getListByEmployeeId(@Param("employeeId")String employeeId);

        @Delete("DELETE FROM equipment_user WHERE equipment_number = #{equipmentNumber}")
        void deleteByEquimentNumber(@Param("equipmentNumber")Integer equipmentNumber);
}
