package com.ecms.web.controller;

import com.ecms.bean.ProductNameInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductNameEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductNameService;
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
@RequestMapping("productName")
public class ProductNameController extends BaseController<ProductNameEntity, ProductNameInfo>{
	@Resource
    private ProductNameService productNameService;

	@Override
	protected BaseService<ProductNameEntity, ProductNameInfo> getBaseService() {
		// TODO Auto-generated method stub
		return productNameService;
	}

	/**
	 * 新增物料名称
	 * @param productNameInfo
	 * @return
	 */
	@RequestMapping("/insertProductName")
	public Result<Void> insertProductName(ProductNameInfo productNameInfo){
		Integer flag = judge(productNameInfo);
		if (flag == 1){
			return ResultUtils.failure("该公司该物料类型下已存在该物料名称");
		}else if (flag == 2){
			return ResultUtils.failure("该公司该物料类型下已存在该物料编号");
		}else {
			productNameService.insertBy(productNameInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 修改物料名称
	 * @param productNameInfo
	 * @return
	 */
	@RequestMapping("/updateProductName")
	public Result<Void> updateProductName(ProductNameInfo productNameInfo){
		productNameInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		//1：已使用 0：未使用
		productNameInfo.setIfUse(USE_STATUS);
		List<ProductNameInfo> typeInfos = productNameService.getListBy(productNameInfo);
		if (typeInfos.size()!=0){
			return ResultUtils.failure("该物料名称已被使用，不能修改");
		}
		Integer flag = judge(productNameInfo);
		if (flag == 0){
			return ResultUtils.failure("该公司下已存在这个物料名称或编号");
		}else{
			productNameService.saveOrUpdate(productNameInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 获取物料名称list
	 * @param productNameInfo
	 * @return
	 */
	@RequestMapping("/searchList")
	public Result<List<ProductNameInfo>> searchList(ProductNameInfo productNameInfo){
		productNameInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		productNameInfo.setStatus(STATUS_DEFAULT);
		List<ProductNameInfo> nameInfoList = productNameService.getListBy(productNameInfo);
		return ResultUtils.success(nameInfoList);
	}

	/**
	 * 分页查询物料名称
	 * @param pageNum
	 * @param pageSize
	 * @param productNameInfo
	 * @return
	 */
	@RequestMapping("/searchProductNamePager")
	public Result<Pager<ProductNameInfo>> searchProductNamePager(int pageNum, int pageSize, ProductNameInfo productNameInfo){
		ExchangeUtils.convertBlank2Null(productNameInfo);
		productNameInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
		Pager<ProductNameInfo> pager = productNameService.getModelListByLike(pageNum,pageSize, productNameInfo,false);
		return ResultUtils.success(pager);
	}

	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchById")
	public Result<ProductNameInfo> searchById(Integer id){
		ProductNameInfo productNameInfo = productNameService.getModel(id);
		return ResultUtils.success(productNameInfo);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		productNameService.updateStatus(id);
		return ResultUtils.success();
	}

	public Integer judge(ProductNameInfo productNameInfo){
		Integer flag = 0;
		ProductNameInfo nameInfo = new ProductNameInfo();
		nameInfo.setProductTypeId(productNameInfo.getProductTypeId());
		nameInfo.setType(productNameInfo.getType());
		nameInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		nameInfo.setStatus(STATUS_DEFAULT);
		List<ProductNameInfo> nameInfos = productNameService.getListBy(nameInfo);
		if (nameInfos.size()!=0){
			flag = 1;
			return flag;
		}
		nameInfo.setType(null);
		nameInfo.setNumber(productNameInfo.getNumber());
		List<ProductNameInfo> nameInfoList = productNameService.getListBy(nameInfo);
		if (nameInfoList.size()!=0){
			flag = 2;
			return flag;
		}
		return flag;
	}
}
