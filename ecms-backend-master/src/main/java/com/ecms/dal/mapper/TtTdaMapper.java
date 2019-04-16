package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.TtTdaEntity;
import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface TtTdaMapper extends BasicMapper<TtTdaEntity> {
    @Results(id = "ttTdaMap", value= {
        @Result(column="td_name", property="tdName"),
        @Result(column="td_odd", property="tdOdd")
    })
    @Select({
        "select ",
        "id, td_name, td_odd",
        "from tt_tda"})
    List<TtTdaEntity> selectAll();
}