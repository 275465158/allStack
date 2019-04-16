package com.ecms.manager;

import com.ecms.bean.SimpleEmployeeInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.dal.mapper.EmployeeMapper;
import com.ecms.web.filter.LoginFilter;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EmployeeManager extends BaseManager<EmployeeEntity> {

	private static final String REDIS_KEY_EMPLOYEE_NUMBER_PREFIX = "number_employee_";

	@Value("${env}")
	private String env;
	@Resource
	private EmployeeMapper employeeMapper;
	@Resource
	private RedisTemplate redisTemplate;

	@Override
	protected BasicMapper<EmployeeEntity> getMapper() {
		return employeeMapper;
	}

	public void removeDepartmentInfo(List<Integer> idList) {
		employeeMapper.removeDepartmentInfo(idList);
	}

	public void removeDepartmentInfo(int id) {
		employeeMapper.removeDepartmentInfoById(id);
	}

	public void removeInvalidPost(int postId) {
		employeeMapper.removeInvalidPost(postId);
	}

	public List<SimpleEmployeeInfo> getSimpleList(int companyId, int departmentId) {
		return employeeMapper.selectSimpleList(companyId, departmentId);
	}

	public void updateDepartmentInfo(int departmentId, int rootDepartmentId, List<Integer> idList) {
		employeeMapper.updateDepartmentInfo(departmentId, rootDepartmentId, idList);
	}

	public List<EmployeeEntity> getEmployeeList(int pageNum, int pageSize, EmployeeEntity employeeEntity) {
		PageMethod.startPage(pageNum, pageSize);
		return employeeMapper.select(employeeEntity);
	}

	public int getNextEmployeeNumber() {
		int companyId = LoginFilter.getCurrentUser().getCompanyId();
		String redisKey = env + "_" + REDIS_KEY_EMPLOYEE_NUMBER_PREFIX + companyId;
		RedisAtomicLong counter = new RedisAtomicLong(redisKey, redisTemplate.getConnectionFactory());
		// 考虑到历史数据，初始值不能从0递增
		if (counter.longValue() == 0) {
			Long maxEmployeeNumber = employeeMapper.getMaxEmployeeNumber(companyId);
			if (maxEmployeeNumber != null && maxEmployeeNumber.longValue() > 1) {
				counter.set(maxEmployeeNumber.longValue());
			}
		}
		return (int)counter.incrementAndGet();
	}

	public List<EmployeeEntity> getByEmployeeName(String name) {
		Condition condition = new Condition(EmployeeEntity.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("status", STATUS_DEFAULT);
		criteria.andEqualTo("companyId", LoginFilter.getCurrentUser().getCompanyId());
		criteria.andLike("name", "%" + name.trim() + "%");
		return getByCondition(condition);
	}
}
