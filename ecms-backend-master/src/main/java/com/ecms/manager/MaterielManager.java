package com.ecms.manager;

import javax.annotation.Resource;

import com.ecms.bean.MaterielInfo;
import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielEntity;
import com.ecms.dal.mapper.MaterielMapper;

import java.util.List;
import java.util.Map;

@Component
public class MaterielManager extends BaseManager<MaterielEntity> {

	@Resource
	private MaterielMapper materielMapper;

	@Override
	protected BasicMapper<MaterielEntity> getMapper() {
		return materielMapper;
	}

	public List<Map<String,Object>> getMaterialGroupByRepertory(String materielCode){
		List<Map<String,Object>> materielList = materielMapper.getMaterialGroupByRepertory(materielCode);
		return materielList;
	}

}
