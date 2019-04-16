package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductParameterNameEntity;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface ProductParameterNameMapper extends BasicMapper<ProductParameterNameEntity> {
    @Results(id = "productParameterNameMap", value= {
        @Result(column="company_id", property="companyId"),
    })
    @Select({
        "select ",
        "id, company_id, name, unit, owner, status, created, updated",
        "from product_parameter_name"})
    List<ProductParameterNameEntity> selectAll();
}