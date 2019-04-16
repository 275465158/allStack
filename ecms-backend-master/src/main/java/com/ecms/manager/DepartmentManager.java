package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.common.BusinessConstants;
import com.ecms.dal.entity.DepartmentEntity;
import com.ecms.dal.mapper.DepartmentMapper;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentManager extends BaseManager<DepartmentEntity> {

	@Resource
	private DepartmentMapper departmentMapper;

	@Override
	protected BasicMapper<DepartmentEntity> getMapper() {
		return departmentMapper;
	}

	public List<DepartmentEntity> getChildren(int id) {
		return departmentMapper.selectChildren(id);
	}

	public DepartmentEntity getByName(String name) {
		DepartmentEntity entity = new DepartmentEntity();
		entity.setDepartmentName(name);
		return departmentMapper.selectOne(entity);
	}

	public List<Integer> getDepartmentIdsByName(String name) {
		Condition condition = new Condition(DepartmentEntity.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("companyId", LoginFilter.getCurrentUser().getCompanyId());
		criteria.andEqualTo("status", BusinessConstants.STATUS_DEFAULT);
		criteria.andLike("departmentName", "%" + name + "%");
		List<DepartmentEntity> list = getByCondition(condition);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.stream().map(DepartmentEntity::getId).collect(Collectors.toList());
		}
	}
}
