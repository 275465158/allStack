package com.ecms.service;

import com.ecms.bean.CompanyInfo;
import com.ecms.dal.entity.CompanyEntity;
import com.ecms.manager.CompanyManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CompanyService {

	@Resource
	private CompanyManager companyManager;

	public void save(CompanyInfo companyInfo) {
		companyManager.saveCompany(ExchangeUtils.exchangeObject(companyInfo, CompanyEntity.class));
	}

	public CompanyInfo getCompany(int id) {
		return ExchangeUtils.exchangeObject(companyManager.getById(id), CompanyInfo.class);
	}
}
