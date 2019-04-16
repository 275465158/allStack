package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionLogDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Richie
 * @version 2019/1/7 14:28
 */
public interface ProductionLogDetailMapper extends BasicMapper<ProductionLogDetailEntity> {

    @Select("<script>SELECT IFNULL(SUM(complete_qty),0) AS completeQty FROM production_log_detail WHERE" +
            " status = 1 AND product_status = 2 " +
            " <if test = 'companyId != null'> AND company_id = #{companyId} </if>" +
            " <if test = 'processId != null'> AND process_id = #{processId} </if>" +
            " <if test = 'productionNumber != null'> AND production_number = #{productionNumber} </if>" +
            " <if test = 'checkFlag != null'> AND check_flag = #{checkFlag} </if></script>")
    Float getCompleteQty(@Param("companyId")Integer companyId, @Param("productionNumber")String productionNumber,
                         @Param("processId")Integer processId,@Param("checkFlag")Integer checkFlag);

    @Select("<script>SELECT IFNULL(SUM(complete_qty),0) AS completeQty FROM production_log_detail WHERE" +
            " status = 1 AND company_id = #{companyId} AND production_number = #{productionNumber} AND process_complete_flag = 1 AND material_id = #{materialId}</script>")
    Float getProductionCompleteQty(@Param("companyId")Integer companyId,@Param("productionNumber")String productionNumber,@Param("materialId")Integer materialId);
}
