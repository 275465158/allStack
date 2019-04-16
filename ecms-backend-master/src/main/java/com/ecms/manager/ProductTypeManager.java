package com.ecms.manager;

import com.ecms.bean.ProductTypeInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductTypeEntity;
import com.ecms.dal.mapper.ProductTypeMapper;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class ProductTypeManager extends BaseManager<ProductTypeEntity> {
	@Resource
	private ProductTypeMapper productTypeMapper ;
	@Override
	protected BasicMapper<ProductTypeEntity> getMapper() {
		// TODO Auto-generated method stub
		return productTypeMapper;
	}
	public ProductTypeInfo getProductTypeByNumber(String number,int companyId){
		return productTypeMapper.getProductTypeByNumber( number,companyId);
	}

	public int getNextProductTypeNumber() {
		int companyId = LoginFilter.getCurrentUser().getCompanyId();
		Long maxProductTypeNumber = productTypeMapper.getMaxProductNumber(companyId);
		return maxProductTypeNumber == null ? 0 : maxProductTypeNumber.intValue() + 1;
	}
	public String getType(int id,int companyId) {
		return productTypeMapper.getType(id,companyId);
	}

}
