package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductAcceptanceDetailEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductAcceptanceDetailMapper extends BasicMapper<ProductAcceptanceDetailEntity> {
    @Results(id = "productAcceptanceDetailMap", value= {
        @Result(column="acceptance_id", property="acceptanceId"),
        @Result(column="check_name", property="checkName"),
        @Result(column="equipment_name", property="equipmentName"),
        @Result(column="equipment_specification", property="equipmentSpecification"),
        @Result(column="min_value", property="minValue"),
        @Result(column="max_value", property="maxValue"),
    })
    @Select({
        "select ",
        "id, acceptance_id, check_name, equipment_name, equipment_specification, unit, ",
        "min_value, max_value, created, updated",
        "from product_acceptance_detail"})
    List<ProductAcceptanceDetailEntity> selectAll();

    @ResultMap("productAcceptanceDetailMap")
    @Select("select * from product_acceptance_detail where acceptance_id = #{acceptanceId}")
    List<ProductAcceptanceDetailEntity> selectByAcceptanceId(int acceptanceId);

    @Update("delete from product_acceptance_detail where acceptance_id = #{acceptanceId}")
    void deleteByAcceptanceId(int acceptanceId);
}