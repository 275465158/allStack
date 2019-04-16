package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.QueryPriceEntity;
import org.apache.ibatis.annotations.Select;

public interface QueryPriceMapper extends BasicMapper<QueryPriceEntity> {
    @Select({"SELECT  id from query_price ORDER BY id desc LIMIT 1"})
    String getNextFileid();

}
