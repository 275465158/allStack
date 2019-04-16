package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.SupplierContactInfo;
import com.ecms.dal.entity.SupplierContactEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SupplierContactManager;

import tk.mybatis.mapper.entity.Example;

@Service
public class SupplierContactService extends BaseService<SupplierContactEntity, SupplierContactInfo> {

	@Resource
	private SupplierContactManager contactManager;
	@Override
	protected BaseManager<SupplierContactEntity> getManager() {
		return contactManager;
	}
	
	public List<SupplierContactInfo> getBySupplierId(int supplierId){
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("supplierId",supplierId);
		List<SupplierContactInfo> list = getByCondition();
		if(!list.isEmpty()) {
			return list;
		}else {
			return null;
		}
		//throw new CommonBusinessException("记录不存在");
	}

}
