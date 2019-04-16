package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcessBaseFormatEntity;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface ProcessBaseFormatMapper extends BasicMapper<ProcessBaseFormatEntity> {
    @Results(id = "processBaseFormatMap", value= {
        @Result(column="process_format_name", property="processFormatName"),
        @Result(column="default_base_format", property="defaultBaseFormat"),
        @Result(column="company_id", property="companyId")
    })
    @Select({
        "select ",
        "id, process_format_name, default_base_format, created, updated, status, company_id",
        "from process_base_format"})
    List<ProcessBaseFormatEntity> selectAll();
}