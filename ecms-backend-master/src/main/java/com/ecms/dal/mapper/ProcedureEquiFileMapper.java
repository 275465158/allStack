package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcedureEquiFileEntity;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface ProcedureEquiFileMapper extends BasicMapper<ProcedureEquiFileEntity> {
    @Results(id = "procedureEquiFileMap", value= {
        @Result(column="procedure_equi_id", property="procedureEquiId"),
        @Result(column="file_name", property="fileName"),
        @Result(column="file_version", property="fileVersion"),
        @Result(column="compile_man", property="compileMan"),
        @Result(column="compile_date", property="compileDate"),
        @Result(column="approval_man", property="approvalMan"),
        @Result(column="approval_result", property="approvalResult"),
        @Result(column="approval_date", property="approvalDate"),
        @Result(column="use_date", property="useDate"),
        @Result(column="company_id", property="companyId")
    })
    @Select({
        "select ",
        "id, procedure_equi_id, file_name, file_version, compile_man, compile_date, approval_man, ",
        "approval_result, approval_date, use_date, remark, created, updated, status, ",
        "company_id",
        "from procedure_equi_file"})
    List<ProcedureEquiFileEntity> selectAll();
    
    @Select({"SELECT  procedure_equi_id from procedure_equi_file ORDER BY id desc LIMIT 1"})
    String getNextFileid();
    
    @Select({"SELECT file_version FROM procedure_equi_file WHERE procedure_equi_id =3#{id}"})
    String findversion(@Param("id")int id);
    
    
}