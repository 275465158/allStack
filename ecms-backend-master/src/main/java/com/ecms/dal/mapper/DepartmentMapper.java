package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DepartmentMapper extends BasicMapper<DepartmentEntity> {

    @Results(id = "departmentMap", value = { @Result(column = "company_id", property = "companyId"),
            @Result(column = "department_number", property = "departmentNumber"),
            @Result(column = "department_name", property = "departmentName"),
            @Result(column = "department_admin_id", property = "departmentAdminId"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "root_id", property = "rootId") })
    @Select("select * from department where status=1 and (parent_id=#{id} or root_id=#{id})")
    List<DepartmentEntity> selectChildren(int id);

    @Select("select ifnull(count(1), 0) from department where company_id=#{companyId}")
    int count(int companyId);
}
