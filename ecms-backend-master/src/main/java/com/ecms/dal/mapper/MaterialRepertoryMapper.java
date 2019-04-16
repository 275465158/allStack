package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 仓库物料mapper
 * @author Richie
 * @version 2018/10/25 17:02
 */
public interface MaterialRepertoryMapper extends BasicMapper<MaterialRepertoryEntity> {
    @Select("SELECT id AS id FROM material_repertory WHERE material_id = #{materialId}")
    Integer getMriId(@Param("materialId") Integer materialId);

    @Select("SELECT id AS id,material_code AS materialCode,material_id AS materialId FROM material_repertory WHERE material_id = #{materialId}")
    List<Map<String,Object>> getListSize(@Param("materialId") Integer materialId);
}
