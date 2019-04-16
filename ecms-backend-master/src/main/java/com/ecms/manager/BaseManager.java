package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.common.BusinessConstants;
import com.ecms.dal.entity.AbstractEntity;
import com.github.pagehelper.page.PageMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public abstract class BaseManager<E extends AbstractEntity> implements BusinessConstants {

	protected Logger logger = LoggerFactory.getLogger(BaseManager.class);

	@Resource
	protected abstract BasicMapper<E> getMapper();

	public E getEntityById(Integer id) {
		return getMapper().selectByPrimaryKey(id);
	}
	
	public E getByExample(Example example) {
		return getMapper().selectOneByExample(example);
	}

	public long getCountBy(E entity) {
		return getMapper().selectCount(entity);
	}

	public List<E> getListBy(int pageNum, int pageSize, E entity) {
		PageMethod.orderBy("id desc");
		PageMethod.startPage(pageNum, pageSize);
		return getMapper().select(entity);
	}

	public List<E> getListBy(E entity) {
		return getMapper().select(entity);
	}

	public List<E> getByCondition(int pageNum, int pageSize, Condition condition) {
		PageMethod.startPage(pageNum, pageSize);
		return getMapper().selectByCondition(condition);
	}

	public List<E> getByCondition(Condition condition) {
		return getMapper().selectByCondition(condition);
	}

	public long getCountByCondition(Condition condition) {
		return getMapper().selectCountByCondition(condition);
	}

	public long getTotal(E entity) {
		return getMapper().selectCount(entity);
	}

	public long getTotal(Condition condition) {
		return getMapper().selectCountByCondition(condition);
	}

	public E insertBy(E entity) {
		getMapper().insertSelective(entity);
		return entity;
	}

	public E updateByPrimaryKeySelective(E entity) {
		entity.setCreated(null);
		entity.setUpdated(new Date());
		getMapper().updateByPrimaryKeySelective(entity);
		return entity;
	}

	public int updateByConditionSelective(E entity, Condition condition) {
		return getMapper().updateByConditionSelective(entity, condition);
	}

	public int deleteByPrimaryKey(Integer id) {
		return getMapper().deleteByPrimaryKey(id);
	}

}
