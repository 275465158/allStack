package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionCheckLogEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Richie
 * @version 2018/11/12 15:22
 */
public interface ProductionCheckLogMapper extends BasicMapper<ProductionCheckLogEntity> {
    @Select({"<script>SELECT IFNULL(SUM(qualified_num),0) AS qualifiedQum FROM production_check_log " +
            "WHERE production_log_id = #{productionLogDetailId} AND company_id = #{companyId}</script>"})
    Float getQualifiedNum(@Param("productionLogDetailId") Integer productionLogDetailId, @Param("companyId") Integer companyId);

    @Select({"<script>SELECT IFNULL(SUM(qualified_num),0) AS qualifiedQum FROM production_check_log " +
            "WHERE production_number = #{productionNumber} AND company_id = #{companyId} AND process_id = #{processId}</script>"})
    Float getCompleteQty(@Param("companyId")Integer companyId,@Param("productionNumber")String productionNumber,@Param("processId")Integer processId);
}
