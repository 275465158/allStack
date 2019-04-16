package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductJobGuideEntity;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface ProductJobGuideMapper extends BasicMapper<ProductJobGuideEntity> {
    @Results(id = "productJobGuideMap", value= {
        @Result(column="company_id", property="companyId"),
        @Result(column="file_no", property="fileNo"),
        @Result(column="file_version", property="fileVersion"),
        @Result(column="file_path", property="filePath"),
    })
    @Select({
        "select ",
        "id, company_id, name, file_no, file_version, file_path, owner, remark, status, ",
        "created, updated",
        "from product_job_guide"})
    List<ProductJobGuideEntity> selectAll();
}