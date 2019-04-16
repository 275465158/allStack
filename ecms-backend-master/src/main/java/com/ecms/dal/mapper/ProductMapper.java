package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionPlanEntity;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper extends BasicMapper<ProductionPlanEntity> {
    @Select("SELECT MAX(CHECK_ORDER) FROM production_plan")
    String getCheckOrder();
}
