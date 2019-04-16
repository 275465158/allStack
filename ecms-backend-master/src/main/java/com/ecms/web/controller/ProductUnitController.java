package com.ecms.web.controller;

import com.ecms.bean.ProductUnitInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductUnitEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductUnitService;
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
@RequestMapping("productUnit")
public class ProductUnitController extends BaseController<ProductUnitEntity, ProductUnitInfo>{
	@Resource
    private ProductUnitService productUnitService;

	@Override
	protected BaseService<ProductUnitEntity, ProductUnitInfo> getBaseService() {
		// TODO Auto-generated method stub
		return productUnitService;
	}

	/**
	 * 新增物料单位
	 * @param productUnitInfo
	 * @return
	 */
	@RequestMapping("/insertProductUnit")
	public Result<Void> insertProductoUnit(ProductUnitInfo productUnitInfo){
		Integer flag = judge(productUnitInfo);
		if (flag == 1){
			return ResultUtils.failure("该公司该物料类型下已存在该单位名称");
		}else if (flag == 2){
			return ResultUtils.failure("该公司该物料类型下已存在该单位编号");
		}else {
			productUnitService.insertBy(productUnitInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 修改物料单位
	 * @param productUnitInfo
	 * @return
	 */
	@RequestMapping("/updateProductUnit")
	public Result<Void> updateProductUnit(ProductUnitInfo productUnitInfo){
		productUnitInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		//1：已使用 0：未使用
		productUnitInfo.setIfUse(USE_STATUS);
		List<ProductUnitInfo> unitInfos = productUnitService.getListBy(productUnitInfo);
		if (unitInfos.size()!=0){
			return ResultUtils.failure("该物料名称已被使用，不能修改");
		}
		Integer flag = judge(productUnitInfo);
		if (flag == 0){
			return ResultUtils.failure("该公司下已存在这个物料单位名称或编号");
		}else{
			productUnitService.saveOrUpdate(productUnitInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 获取物料单位list
	 * @param productUnitInfo
	 * @return
	 */
	@RequestMapping("/searchList")
	public Result<List<ProductUnitInfo>> searchList(ProductUnitInfo productUnitInfo){
		productUnitInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		productUnitInfo.setStatus(STATUS_DEFAULT);
		List<ProductUnitInfo> unitInfos = productUnitService.getListBy(productUnitInfo);
		return ResultUtils.success(unitInfos);
	}

	/**
	 * 分页查询物料单位
	 * @param pageNum
	 * @param pageSize
	 * @param productUnitInfo
	 * @return
	 */
	@RequestMapping("/searchProductUnitPager")
	public Result<Pager<ProductUnitInfo>> searchProductUnitPager(int pageNum, int pageSize, ProductUnitInfo productUnitInfo){
		ExchangeUtils.convertBlank2Null(productUnitInfo);
		productUnitInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
		Pager<ProductUnitInfo> pager = productUnitService.getModelListByLike(pageNum,pageSize, productUnitInfo,false);
		return ResultUtils.success(pager);
	}

	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchById")
	public Result<ProductUnitInfo> searchById(Integer id){
		ProductUnitInfo productUnitInfo = productUnitService.getModel(id);
		return ResultUtils.success(productUnitInfo);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		productUnitService.updateStatus(id);
		return ResultUtils.success();
	}

	public Integer judge(ProductUnitInfo productUnitInfo){
		Integer flag = 0;
		ProductUnitInfo unitInfo = new ProductUnitInfo();
		unitInfo.setProductOriginalId(productUnitInfo.getProductOriginalId());
		unitInfo.setType(productUnitInfo.getType());
		unitInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		unitInfo.setStatus(STATUS_DEFAULT);
		List<ProductUnitInfo> unitInfos = productUnitService.getListBy(unitInfo);
		if (unitInfos.size()!=0){
			flag = 1;
			return flag;
		}
		unitInfo.setType(null);
		unitInfo.setNumber(productUnitInfo.getNumber());
		List<ProductUnitInfo> unitInfoList = productUnitService.getListBy(unitInfo);
		if (unitInfoList.size()!=0){
			flag = 2;
			return flag;
		}
		return flag;
	}
}
