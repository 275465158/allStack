package com.ecms.manager;

import com.ecms.dal.entity.CompanyEntity;
import com.ecms.dal.mapper.CompanyMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CompanyManager {

	@Resource
	private CompanyMapper companyMapper;

	public void saveCompany(CompanyEntity companyEntity) {
		if (getById(companyEntity.getId()) == null) {
			companyMapper.insertSelective(companyEntity);
		} else {
			companyMapper.updateByPrimaryKeySelective(companyEntity);
		}
	}

	public CompanyEntity getById(int id) {
		return companyMapper.selectByPrimaryKey(id);
	}
}
