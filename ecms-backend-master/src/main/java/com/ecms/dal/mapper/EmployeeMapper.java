package com.ecms.dal.mapper;

import com.ecms.bean.SimpleEmployeeInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EmployeeMapper extends BasicMapper<EmployeeEntity> {

    @Update("<script>"
             + "update employee set root_department_id=null, department_id=null where department_id in "
             + "<foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>"
             + "#{item}"
             + "</foreach>"
             + "</script>")
    void removeDepartmentInfo(@Param("idList")List<Integer> idList);

    @Update("update employee set root_department_id=null, department_id=null where id=#{id}")
    void removeDepartmentInfoById(int id);

    @Update("<script>"
            + "update employee set root_department_id=#{rDId}, department_id=#{dId} where id in "
            + "<foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    void updateDepartmentInfo(@Param("dId")int departmentId, @Param("rDId")int rootDepartmentId, @Param("idList")List<Integer> idList);

    @Update("update employee set post_id=null where post_id=#{postId}")
    void removeInvalidPost(int postId);

    @Select("select id, name, employee_number as employeeNumber, department_id as departmentId from employee " +
            "where company_id=#{companyId} and (department_id != #{departmentId} or department_id is null) and status=1 " +
            "order by case when department_id is null then 0 else 1 end, id")
    List<SimpleEmployeeInfo> selectSimpleList(@Param("companyId")int companyId, @Param("departmentId")int departmentId);

    @Select("select max(employee_number) from employee where company_id=#{companyId}")
    Long getMaxEmployeeNumber(int companyId);
}
