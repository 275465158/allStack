/**
 * 
 */
package com.ecms.dal.mapper;

import com.ecms.bean.ProductTypeInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductTypeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author pratice
 *
 */
public interface ProductTypeMapper extends BasicMapper<ProductTypeEntity> {

    @Select("select max(number) from product_type where company_id=#{companyId}")
    Long getMaxProductNumber(int companyId);
    @Select("select type from product_type where id=#{id} and company_id=#{companyId} ")
     String getType(@Param("id")int id,@Param("companyId")int companyId);
    @Results(id = "productTypeMapper", value = {
            @Result(column = "company_id", property = "companyId"),
            @Result(column = "type", property = "type"),
            @Result(column = "number", property = "number")})
    @Select("select * from product_type where company_id=#{companyId} and status=1 and number=#{number}")
    ProductTypeInfo getProductTypeByNumber(@Param("number") String number, @Param("companyId") int companyId);
}
