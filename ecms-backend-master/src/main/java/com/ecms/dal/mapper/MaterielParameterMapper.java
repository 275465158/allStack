package com.ecms.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielParameterEntity;

public interface MaterielParameterMapper extends BasicMapper<MaterielParameterEntity> {
	@Select("SELECT distinct(parameter_name) as parameterName FROM ecms.materielparameter where company_id = #{companyId}")
	@Results(id="parameterNameList", value= {@Result(column="parameterName", property="parameterName")})
	List<MaterielParameterEntity> getListParameterName(Integer companyId);

}
