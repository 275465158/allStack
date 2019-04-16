package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionLogEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProductionLogMapper extends BasicMapper<ProductionLogEntity> {

    @Select("SELECT SUM(completed_qty) AS completedQty FROM production_log WHERE product_batch = #{orderCode} AND material_id = #{materialId} AND company_id = #{companyId} AND status = 1")
    Float getCompletedQty(@Param("orderCode") String orderCode, @Param("materialId") Integer materialId, @Param("companyId") Integer companyId);
}
