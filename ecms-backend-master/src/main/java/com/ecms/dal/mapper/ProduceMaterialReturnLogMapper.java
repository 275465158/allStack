package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProduceMaterialReturnLogEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Richie
 * @version 2019/1/9 13:58
 */
public interface ProduceMaterialReturnLogMapper extends BasicMapper<ProduceMaterialReturnLogEntity> {
    @Select({"<script>SELECT IFNULL(SUM(return_qty),0) AS returnQty FROM produce_material_return_log " +
            "WHERE production_number = #{productionNumber} AND company_id = #{companyId} AND material_id = #{materialId} </script>"})
    Float getReturnedQty(@Param("materialId")Integer materialId, @Param("productionNumber")String productionNumber, @Param("companyId")Integer companyId);
}
