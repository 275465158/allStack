package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionIssueDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Richie
 * @version 2018/9/16 17:07
 */
public interface ProductionIssueDetailMapper extends BasicMapper<ProductionIssueDetailEntity> {

    @Select({"<script>SELECT IFNULL(SUM(issue_qty),0) AS issueQty FROM produce_issuance_detail " +
            "WHERE production_number = #{productionNumber} AND company_id = #{companyId} AND material_id = #{materialId} " +
            "GROUP BY production_number</script>"})
    Float getIssueQty(@Param("materialId")Integer materialId,@Param("productionNumber")String productionNumber, @Param("companyId")Integer companyId);
}
