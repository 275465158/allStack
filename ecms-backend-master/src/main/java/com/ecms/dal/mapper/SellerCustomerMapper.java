package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerCustomerEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SellerCustomerMapper extends BasicMapper<SellerCustomerEntity> {

    @Select("select address_province from seller_customer where company_id=#{companyId} and status=1 group by address_province")
    List<String> selectProvinces(int companyId);
}
