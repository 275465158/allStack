package com.ecms.web.controller;

import com.ecms.bean.ProductionPlanInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductionPlanEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import static com.ecms.common.BusinessConstants.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("product")
public class ProductController extends BaseController<ProductionPlanEntity,ProductionPlanInfo> {

	@Resource
	private ProductService productService;

	@Override
	protected BaseService<ProductionPlanEntity, ProductionPlanInfo> getBaseService() {
		return productService;
	}

	/**
	 * 检测更新
	 * @param productionPlanInfo
	 * @return
	 */
	@RequestMapping("/productUpdate")
	public Result<Void> updateProduct(ProductionPlanInfo productionPlanInfo){
		productService.updateProduct(productionPlanInfo);
		return ResultUtils.success();
	}

	/**
	 * 待检测界面/查询全部/按条件查询
	 * @param pageNum
	 * @param productionPlanInfo
	 * @return
	 */
	@RequestMapping("/searchProductList")
	public Result<Pager<ProductionPlanInfo>> getModelData(int pageNum, ProductionPlanInfo productionPlanInfo) {
		ExchangeUtils.convertBlank2Null(productionPlanInfo);
		productionPlanInfo.setCheckFlag(PRODUCT_UNCHECK_FLAG);//查询未检测的数据
		Pager<ProductionPlanInfo> pager = productService.getModelList(pageNum, DEFAULT_PAGE_SIZE, productionPlanInfo);
		return ResultUtils.success(pager);
	}

	/**
	 * 检测记录界面查询
	 * @param pageNum
	 * @param productionPlanInfo
	 * @return
	 */
	@RequestMapping("/productList")
	public Result<Pager<ProductionPlanInfo>> getAllModelData(int pageNum, ProductionPlanInfo productionPlanInfo) {
		ExchangeUtils.convertBlank2Null(productionPlanInfo);
		productionPlanInfo.setCheckFlag(PRODUCT_CHECK_FLAG);//查询检测过的记录
		Pager<ProductionPlanInfo> pager = productService.getModelList(pageNum, DEFAULT_PAGE_SIZE, productionPlanInfo);
		return ResultUtils.success(pager);
	}
}
