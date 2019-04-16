package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductDrawingEntity;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface ProductDrawingMapper extends BasicMapper<ProductDrawingEntity> {
    @Results(id = "productDrawingMap", value= {
        @Result(column="company_id", property="companyId"),
        @Result(column="drawing_remark", property="drawingRemark"),
        @Result(column="technology_remark", property="technologyRemark"),
        @Result(column="cad_file", property="cadFile"),
        @Result(column="pdf_file", property="pdfFile"),
        @Result(column="technology_file", property="technologyFile"),
        @Result(column="check_name", property="checkName"),
        @Result(column="check_date", property="checkDate"),
        @Result(column="check_result", property="checkResult"),
    })
    @Select({
        "select ",
        "id, company_id, name, drawing_remark, technology_remark, owner, cad_file, pdf_file, ",
        "technology_file, check_name, check_date, check_result, status, created, updated",
        "from product_drawing"})
    List<ProductDrawingEntity> selectAll();
}