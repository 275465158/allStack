package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductAcceptanceEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductAcceptanceMapper extends BasicMapper<ProductAcceptanceEntity> {
    @Results(id = "productAcceptanceMap", value= {
        @Result(column="company_id", property="companyId"),
    })
    @Select({
        "select ",
        "id, company_id, name, owner, status, created, updated",
        "from product_acceptance"})
    List<ProductAcceptanceEntity> selectAll();

}