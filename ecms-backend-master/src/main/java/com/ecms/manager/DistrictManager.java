package com.ecms.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.dal.entity.DistrictEntity;
import com.ecms.dal.mapper.DistrictMapper;
import com.github.pagehelper.page.PageMethod;

import tk.mybatis.mapper.entity.Condition;

@Component
public class DistrictManager {

	@Resource
	private DistrictMapper mapper;

	public DistrictEntity getEntityById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public long getCountBy(DistrictEntity entity) {
		return mapper.selectCount(entity);
	}
	
	public List<DistrictEntity> getListBy(int pageNum, int pageSize, DistrictEntity entity){
		PageMethod.startPage(pageNum, pageSize);
		return getListBy(entity);
	}
	
	public List<DistrictEntity> getListBy(DistrictEntity entity){
		return mapper.select(entity);
	}
	
	public List<DistrictEntity> getByCondition(int pageNum, int pageSize, Condition condition){
		PageMethod.startPage(pageNum, pageSize);
		return getByCondition(condition);
	}
	
	public List<DistrictEntity> getByCondition(Condition condition){
		return mapper.selectByCondition(condition);
	}
	
	public long getCountByCondition(Condition condition) {
		return mapper.selectCountByCondition(condition);
	}
	
	public long getTotal(DistrictEntity entity) {
		return mapper.selectCount(entity);
	}

}
