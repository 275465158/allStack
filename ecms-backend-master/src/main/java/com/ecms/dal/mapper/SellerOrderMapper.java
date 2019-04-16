package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerOrderEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SellerOrderMapper extends BasicMapper<SellerOrderEntity> {
    @Update("UPDATE seller_order SET progress = 4 WHERE contract_id = #{contractId}")
    void updateByContractId(@Param("contractId")String contractId);

    @Select("SELECT COUNT(1) FROM seller_order WHERE company_id = #{companyId}")
    Integer getCount(@Param("companyId")Integer companyId);
}
