package com.ecms.web.controller;

import com.ecms.bean.ProductOriginalInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductOriginalEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductOriginalService;
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
@RequestMapping("productOriginal")
public class ProductOriginalController extends BaseController<ProductOriginalEntity, ProductOriginalInfo>{
	@Resource
    private ProductOriginalService productOriginalService;

	@Override
	protected BaseService<ProductOriginalEntity, ProductOriginalInfo> getBaseService() {
		// TODO Auto-generated method stub
		return productOriginalService;
	}

	/**
	 * 新增物料材料
	 * @param productOriginalInfo
	 * @return
	 */
	@RequestMapping("/insertProductOriginal")
	public Result<Void> insertProductoOriginal(ProductOriginalInfo productOriginalInfo){
		Integer flag = judge(productOriginalInfo);
		if (flag == 1){
			return ResultUtils.failure("该公司该物料类型下已存在该材料名称");
		}else if (flag == 2){
			return ResultUtils.failure("该公司该物料类型下已存在该材料编号");
		}else {
			productOriginalService.insertBy(productOriginalInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 修改物料材料
	 * @param productOriginalInfo
	 * @return
	 */
	@RequestMapping("/updateProductOriginal")
	public Result<Void> updateProductOriginal(ProductOriginalInfo productOriginalInfo){
		productOriginalInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		//1：已使用 0：未使用
		productOriginalInfo.setIfUse(USE_STATUS);
		List<ProductOriginalInfo> typeInfos = productOriginalService.getListBy(productOriginalInfo);
		if (typeInfos.size()!=0){
			return ResultUtils.failure("该物料名称已被使用，不能修改");
		}
		Integer flag = judge(productOriginalInfo);
		if (flag == 0){
			return ResultUtils.failure("该公司下已存在这个材料名称或编号");
		}else{
			productOriginalService.saveOrUpdate(productOriginalInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 获取物料材料list
	 * @param productOriginalInfo
	 * @return
	 */
	@RequestMapping("/searchList")
	public Result<List<ProductOriginalInfo>> searchList(ProductOriginalInfo productOriginalInfo){
		productOriginalInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		productOriginalInfo.setStatus(STATUS_DEFAULT);
		List<ProductOriginalInfo> originalInfos = productOriginalService.getListBy(productOriginalInfo);
		return ResultUtils.success(originalInfos);
	}

	/**
	 * 分页查询物料材料
	 * @param pageNum
	 * @param pageSize
	 * @param productOriginalInfo
	 * @return
	 */
	@RequestMapping("/searchProductOriginalPager")
	public Result<Pager<ProductOriginalInfo>> searchProductOriginalPager(int pageNum, int pageSize, ProductOriginalInfo productOriginalInfo){
		ExchangeUtils.convertBlank2Null(productOriginalInfo);
		productOriginalInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
		Pager<ProductOriginalInfo> pager = productOriginalService.getModelListByLike(pageNum,pageSize, productOriginalInfo,false);
		return ResultUtils.success(pager);
	}

	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchById")
	public Result<ProductOriginalInfo> searchById(Integer id){
		ProductOriginalInfo productOriginalInfo = productOriginalService.getModel(id);
		return ResultUtils.success(productOriginalInfo);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		productOriginalService.updateStatus(id);
		return ResultUtils.success();
	}

	public Integer judge(ProductOriginalInfo productOriginalInfo){
		Integer flag = 0;
		ProductOriginalInfo originalInfo = new ProductOriginalInfo();
		originalInfo.setProductNameId(productOriginalInfo.getProductNameId());
		originalInfo.setType(productOriginalInfo.getType());
		originalInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		originalInfo.setStatus(STATUS_DEFAULT);
		List<ProductOriginalInfo> originalInfos = productOriginalService.getListBy(originalInfo);
		if (originalInfos.size()!=0){
			flag = 1;
			return flag;
		}
		originalInfo.setType(null);
		originalInfo.setNumber(productOriginalInfo.getNumber());
		List<ProductOriginalInfo> originalInfoList = productOriginalService.getListBy(originalInfo);
		if (originalInfoList.size()!=0){
			flag = 2;
			return flag;
		}
		return flag;
	}
}
