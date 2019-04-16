package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductParameterSpecificationEntity;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface ProductParameterSpecificationMapper extends BasicMapper<ProductParameterSpecificationEntity> {
    @Results(id = "productParameterSpecificationMap", value= {
        @Result(column="company_id", property="companyId"),
        @Result(column="parameter_name", property="parameterName"),
        @Result(column="parameter_unit", property="parameterUnit"),
        @Result(column="parameter_number", property="parameterNumber"),
    })
    @Select({
        "select ",
        "id, company_id, name, parameter_name, parameter_unit, parameter_number, owner, ",
        "maintainer, status, created, updated",
        "from product_parameter_specification"})
    List<ProductParameterSpecificationEntity> selectAll();
}