package com.ecms.dal.mapper;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcedureEquiEntity;

public interface ProcedureEquiMapper extends BasicMapper<ProcedureEquiEntity> {
	@Select("SELECT  id from procedure_equi ORDER BY id desc LIMIT 1")
	@Results(id = "id")
	Integer getProcedureNum();
}