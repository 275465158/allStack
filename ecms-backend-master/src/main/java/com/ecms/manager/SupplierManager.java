package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SupplierEntity;
import com.ecms.dal.mapper.SupplierMapper;

import java.util.List;

@Component
public class SupplierManager extends BaseManager<SupplierEntity> {

	@Resource
	private SupplierMapper supplierMapper;

	@Override
	protected BasicMapper<SupplierEntity> getMapper() {
		return supplierMapper;
	}

	public Integer getNextId() {
		if (StringUtils.isEmpty(supplierMapper.getNextId())) {
			return 1;
		} else {
			return supplierMapper.getNextId() + 1;
		}
	}

	public List<String> getAllCode() {
		return supplierMapper.getAllCode();
	}
}
