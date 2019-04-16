package com.ecms.common;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;

public interface BasicMapper<T> extends Mapper<T>, ConditionMapper<T> {
}
