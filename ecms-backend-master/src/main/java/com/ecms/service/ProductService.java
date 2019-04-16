package com.ecms.service;

import com.ecms.bean.ProductionPlanInfo;
import com.ecms.dal.entity.ProductionPlanEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ProductService extends BaseService<ProductionPlanEntity,ProductionPlanInfo>{

	@Resource
	private ProductManager productManager;

	@Override
	protected BaseManager<ProductionPlanEntity> getManager(){
		return productManager;
	}


    public void updateProduct(ProductionPlanInfo productionPlanInfo) {
		ProductionPlanEntity productionPlanEntity = ExchangeUtils.exchangeObject(productionPlanInfo,ProductionPlanEntity.class);
		String userName = LoginFilter.getCurrentUser().getUserName();
		String checkOrder = productManager.getCheckOrder();
		if (checkOrder == null){
			checkOrder = "1";
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String getDate =sdf.format(new Date());
		productionPlanEntity.setCheckFlag(PRODUCT_CHECK_FLAG);
		productManager.updateProduct(productionPlanEntity);
	}
}
