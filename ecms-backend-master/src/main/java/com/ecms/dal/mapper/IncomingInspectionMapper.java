package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.IncomingInspectionEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IncomingInspectionMapper extends BasicMapper<IncomingInspectionEntity>{
        @Select("select * from incoming_inspection")
        @Results(id = "incomingMap", value = {
                @Result(column = "order_number", property = "orderNumber"),
                @Result(column = "supplier_name", property = "supplierName"),
                @Result(column = "materiel_number", property = "materielNumber"),
                @Result(column = "materiel_name", property = "materielName"),
                @Result(column = "raw_material", property = "rawMaterial"),
                @Result(column = "parameter1", property = "parameter1"),
                @Result(column = "parameter2", property = "parameter2"),
                @Result(column = "parameter3", property = "parameter3"),
                @Result(column = "parameter4", property = "parameter4"),
                @Result(column = "parameter5", property = "parameter5"),
                @Result(column = "total_number", property = "totalNumber"),
                @Result(column = "total_unqualified_number", property = "totalUnqualifiedNumber"),
                @Result(column = "production_type", property = "productionType"),
                @Result(column = "product_name", property = "productName"),
                @Result(column = "production_date", property = "productionDate"),
                @Result(column = "materiel_status", property = "materielStatus"),
                @Result(column = "order_reason", property = "orderReason"),
                @Result(column = "qualified_percent", property = "qualifiedPercent"),
                @Result(column = "total_qualified_number", property = "totalQualifiedNumber"),
        })
        List<IncomingInspectionEntity> tt();

        @Select("select * from incoming_inspection where user_account=#{account}")
        @ResultMap("incomingMap")
        IncomingInspectionEntity selectByAccount(String account);
}
