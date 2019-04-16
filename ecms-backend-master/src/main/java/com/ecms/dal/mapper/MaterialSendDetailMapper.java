package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialSendDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Richie
 * @version 2018/11/29 13:35
 */
public interface MaterialSendDetailMapper extends BasicMapper<MaterialSendDetailEntity> {

    @Select("SELECT SUM(send_qty) AS sendQty FROM material_send_detail WHERE material_id = #{materialId} AND company_id = #{companyId} AND contract_id = #{contractId} AND order_code = #{orderCode} AND seller_order_product_id = #{sellerOrderProductId}")
    Float getAlreadySendQty(@Param("companyId")Integer companyId, @Param("contractId")String contractId, @Param("orderCode")String orderCode, @Param("materialId")Integer materialId,@Param("sellerOrderProductId")Integer sellerOrderProductId);
}
