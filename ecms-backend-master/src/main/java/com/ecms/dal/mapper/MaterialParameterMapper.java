package com.ecms.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialParameterEntity;

public interface MaterialParameterMapper extends BasicMapper<MaterialParameterEntity> {
	@Select("SELECT distinct(parameter_name) as parameterName FROM ecms.material_parameter where company_id = #{companyId}")
	@Results(id="parameterNameList", value= {@Result(column="parameterName", property="parameterName")})
	List<MaterialParameterEntity> getListParameterName(Integer companyId);

}
