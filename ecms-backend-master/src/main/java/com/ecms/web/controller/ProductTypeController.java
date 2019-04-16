package com.ecms.web.controller;

import com.ecms.bean.ProductTypeInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductTypeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductTypeService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;
import static com.ecms.common.BusinessConstants.USE_STATUS;

@RestController
@RequestMapping("productType")
public class ProductTypeController extends BaseController<ProductTypeEntity, ProductTypeInfo>{
	@Resource
    private ProductTypeService productTypeService;

	@Override
	protected BaseService<ProductTypeEntity, ProductTypeInfo> getBaseService() {
		// TODO Auto-generated method stub
		return productTypeService;
	}
	/*
	 * 修改产品类型状态
	 * */
	@RequestMapping("toggle")
	public Result<Void> togglestatus(ProductTypeInfo info){
			productTypeService.saveOrUpdate(info);
			return ResultUtils.success();
	}
	/**1.增加产品类型
	 * @param info
	 * @return
	 */
	@RequestMapping("inserttype")
	public Result<Void> insertstatus(ProductTypeInfo info){
		ProductTypeInfo tempinfo=new ProductTypeInfo();
		tempinfo.setType(info.getType());
		List<ProductTypeInfo> ProductTypeList = productTypeService.getListBy(tempinfo);
		if (ProductTypeList.size()!=0) {
			return ResultUtils.failure("验收名称不可重复!");
		}else {
			productTypeService.saveOrUpdate(info);
			return ResultUtils.success();
		}
	}

	/****
	 * 查询产品类型
	 * @param pageNum
	 * @param pageSize
	 * @param productTypeInfo
	 * @return
	 */
	@RequestMapping("searchTypePage")
	public Result<Pager<ProductTypeInfo>> searchTypePage(int pageNum,int pageSize,ProductTypeInfo productTypeInfo){
		ExchangeUtils.convertBlank2Null(productTypeInfo);
		productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		Pager<ProductTypeInfo> pager = productTypeService.getModelListByLike(pageNum,pageSize, productTypeInfo, true);
		return ResultUtils.success(pager);
	}
	 /** 询价专用查找物料类型
	   * @Author:pratice
	   * @date 2018/12/29
	   * @Description:
	   */
	@RequestMapping("searchTypeForQuery")
	public Result<Pager<ProductTypeInfo>> searchTypeFromQuery(int pageNum,int pageSize,ProductTypeInfo productTypeInfo){
		ExchangeUtils.convertBlank2Null(productTypeInfo);
		productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
		Pager<ProductTypeInfo> pager = productTypeService.getModelList(pageNum,pageSize, productTypeInfo);
		return ResultUtils.success(pager);
	}


	/**
	 * 停用/启用产品类型
	 * @param id
	 */
	@RequestMapping("/updateStatus")
	public Result<Void> updateStatus(Integer id){
		productTypeService.updateStatus(id);
		return ResultUtils.success();
	}

	/****
	 * 新增产品类型
	 * @param productTypeInfo
	 * @return
	 */
	@RequestMapping("/saveProductType")
	public Result<Void> saveProductType(ProductTypeInfo productTypeInfo){
		productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<ProductTypeInfo> ptiList = productTypeService.getListBy(productTypeInfo);
		if (ptiList.size()!=0){
			return ResultUtils.failure("该公司下已有这个产品类型");
		}else {
			productTypeInfo.setProductNum(productTypeService.getNextNumber());
			productTypeService.saveProductType(productTypeInfo);
		}
		return ResultUtils.success();
	}

	@RequestMapping("/searchList")
	public Result<List<ProductTypeInfo>> searchList(){
		List<ProductTypeInfo> list = productTypeService.getValidList();
		return ResultUtils.success(list);
	}

	/**
	 * 新增物料类型
	 * @param productTypeInfo
	 * @return
	 */
	@RequestMapping("/insertProductType")
	public Result<Void> insertProductType(ProductTypeInfo productTypeInfo){
		Integer flag = judge(productTypeInfo);
		if (flag == 1){
			return ResultUtils.failure("该公司下已存在这个物料类型名称");
		}else if (flag == 2){
			return ResultUtils.failure("该公司下已存在这个物料类型编号");
		} else {
			productTypeService.insertBy(productTypeInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 修改物料类型
	 * @param productTypeInfo
	 * @return
	 */
	@RequestMapping("/updateProductType")
	public Result<Void> updateProductType(ProductTypeInfo productTypeInfo){
		productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		//1：已使用 0：未使用
		productTypeInfo.setIfUse(USE_STATUS);
		List<ProductTypeInfo> typeInfos = productTypeService.getListBy(productTypeInfo);
		if (typeInfos.size()!=0){
			return ResultUtils.failure("该物料类型已被使用，不能修改");
		}
		Integer flag = judge(productTypeInfo);
		if (flag == 0){
			return ResultUtils.failure("该公司下已存在这个物料类型或编号");
		}else{
			productTypeService.saveOrUpdate(productTypeInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 分页查询物料类型
	 * @param pageNum
	 * @param pageSize
	 * @param productTypeInfo
	 * @return
	 */
	@RequestMapping("/searchProductTypePager")
	public Result<Pager<ProductTypeInfo>> searchProductTypePager(int pageNum,int pageSize,ProductTypeInfo productTypeInfo){
		ExchangeUtils.convertBlank2Null(productTypeInfo);
		productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
		Pager<ProductTypeInfo> pager = productTypeService.getModelListByLike(pageNum,pageSize, productTypeInfo,true);
		return ResultUtils.success(pager);
	}

	/**
	 * 获取类型list
	 * @param productTypeInfo
	 * @return
	 */
	@RequestMapping("/searchAllList")
	public Result<List<ProductTypeInfo>> searchAllList(){
		ProductTypeInfo productTypeInfo = new ProductTypeInfo();
		productTypeInfo.setStatus(STATUS_DEFAULT);
		productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<ProductTypeInfo> typeInfoList = productTypeService.getListBy(productTypeInfo);
		return ResultUtils.success(typeInfoList);
	}

	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchById")
	public Result<ProductTypeInfo> searchById(Integer id){
		ProductTypeInfo productTypeInfo = productTypeService.getModel(id);
		return ResultUtils.success(productTypeInfo);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		productTypeService.updateStatus(id);
		return ResultUtils.success();
	}

	public Integer judge(ProductTypeInfo productTypeInfo){
		Integer flag = 0;
		ProductTypeInfo typeInfo = new ProductTypeInfo();
		typeInfo.setType(productTypeInfo.getType());
		typeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		typeInfo.setStatus(STATUS_DEFAULT);
		//线判断名称是否存在
		List<ProductTypeInfo> typeInfos = productTypeService.getListBy(productTypeInfo);
		if (typeInfos.size()!=0){
			flag = 1;
			return flag;
		}
		//然后判断编号是否存在
		typeInfo.setType(null);
		typeInfo.setNumber(productTypeInfo.getNumber());
		List<ProductTypeInfo> typeInfoList = productTypeService.getListBy(productTypeInfo);
		if (typeInfoList.size()!=0){
			flag = 2;
			return flag;
		}
		return flag;
	}

}
