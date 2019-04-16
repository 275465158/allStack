package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionIssueEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Richie
 * @version 2018/9/16 17:07
 */
public interface ProductionIssueMapper extends BasicMapper<ProductionIssueEntity> {
    @Select({"<script>SELECT IFNULL(SUM(issue_qty)-SUM(used_qty),0) AS issueQty from produce_issuance WHERE material_id = #{materialId} </script>"})
    Float getIssueQty(@Param("materialId")Integer materialId);
}
