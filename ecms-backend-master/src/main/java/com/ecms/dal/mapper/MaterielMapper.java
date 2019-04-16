package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MaterielMapper extends BasicMapper<MaterielEntity> {

    @Select("SELECT a.id AS id,a.materiel_code AS materielCode," +
            "a.materiel_name AS materielName,sum(a.onhand)AS totalOnhand,a.repertory_id as repertoryId," +
            "sum(a.required_by_orders) AS totalRequiredByOrders FROM materiel a where a.materiel_code = #{materielCode} group by a.repertory_id")
    List<Map<String,Object>> getMaterialGroupByRepertory(@Param("materielCode") String materielCode);
}
