package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRelationEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface MaterialRelationMapper extends BasicMapper<MaterialRelationEntity> {

    @Update("UPDATE material_relation SET `material_child_id` = #{materialMergeId} WHERE material_child_id = #{materialId} AND company_id = #{companyId}")
    void updateByMaterialChildIdAndCompanyId(@Param("materialId")Integer materialId,@Param("companyId")Integer companyId,@Param("materialMergeId")Integer materialMergeId);
}
