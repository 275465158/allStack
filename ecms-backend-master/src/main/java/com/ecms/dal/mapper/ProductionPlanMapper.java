package com.ecms.dal.mapper;

import com.ecms.bean.ProductionPlanInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionPlanEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductionPlanMapper extends BasicMapper<ProductionPlanEntity> {

    @Select("SELECT production_number AS productBatch FROM production_plan WHERE material_id = #{materialId} AND status = 1")
    List<ProductionPlanInfo> getProductionBatchList(@Param("materialId")int materialId);

    @Delete("DELETE FROM production_plan WHERE order_code = #{orderCode} AND production_status = 1")
    void deleteByOrderCode(@Param("orderCode")String orderCode);

    @Select("SELECT COUNT(*) FROM seller_order_log WHERE order_id = #{orderId} and plan_flag = 0")
    Integer getSellerOrderLogSize(@Param("orderId")Integer orderId);

    @Select("SELECT COUNT(*) FROM production_plan WHERE order_code = #{orderCode}")
    Integer getProductionPlanSize(@Param("orderCode")String orderCode);

    @Update("update seller_order_log set plan_flag = 1 WHERE order_id = #{orderId}")
    void updatePlanFlagByOrderId(@Param("orderId")Integer orderId);

    @Select("SELECT id AS id, production_number AS productionNumber FROM production_plan WHERE material_id = #{materialId} and contract_id = #{contractId} AND issue_qty < material_num")
    List<ProductionPlanInfo> getProductionNumberList(@Param("materialId")int materialId,@Param("contractId")String contractId);

    @Update("update production_plan set status = 0 WHERE issue_qty = 0 AND order_code = #{orderCode}")
    void deleteProductionPlan(@Param("orderCode")String orderCode);
}
