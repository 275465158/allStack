package com.ecms.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ecms.bean.CheckLogInfo;
import com.ecms.bean.MaterialCheckInfo;
import com.ecms.service.CheckLogService;
import com.ecms.service.MaterialCheckService;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.ProductAcceptanceDetailInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductAcceptanceDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.utils.ResultUtils;
@RestController
@RequestMapping("productAcceptanceDetail")
public class ProductAcceptanceDetailController extends BaseController<ProductAcceptanceDetailEntity, ProductAcceptanceDetailInfo> {
	@Resource
    private BaseService<ProductAcceptanceDetailEntity, ProductAcceptanceDetailInfo> productAcceptanceDetailService;

	@Resource
	private MaterialCheckService materialCheckService;

	@Resource
	private CheckLogService checkLogService;

	@Override
	protected BaseService<ProductAcceptanceDetailEntity, ProductAcceptanceDetailInfo> getBaseService() {
		// TODO Auto-generated method stub
		return productAcceptanceDetailService;
	}
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping("checkdetailinfo")
	public Result<List<ProductAcceptanceDetailInfo>> checkdetailinfo
	(@RequestParam(value = "checkId", required = false) int id) {
		if (id > 0) {
			ProductAcceptanceDetailInfo tempinfo=new ProductAcceptanceDetailInfo();
			tempinfo.setAcceptanceId(id);
			List<ProductAcceptanceDetailInfo> list = productAcceptanceDetailService.getListBy(tempinfo);
			return ResultUtils.success(list);
		}else {
			return ResultUtils.failure("id错误!");
		}
	}

	@RequestMapping("/searchAcceptanceDetail")
	public Result<List<ProductAcceptanceDetailInfo>> searchAcceptanceDetail(Integer materialId){
		MaterialCheckInfo mci = new MaterialCheckInfo();
		mci.setMaterialId(materialId);
		mci.setStatus(1);
		mci.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<MaterialCheckInfo> mciList = materialCheckService.getListBy(mci);
		List<ProductAcceptanceDetailInfo> padiList = new ArrayList<>();
		if (mciList.size()!=0){
			Integer checkId = mciList.get(0).getCheckId();
			ProductAcceptanceDetailInfo padi = new ProductAcceptanceDetailInfo();
			padi.setAcceptanceId(checkId);
			padiList = productAcceptanceDetailService.getListBy(padi);
		}
		return ResultUtils.success(padiList);
	}

	@RequestMapping("/searchListById")
	public Result<List<ProductAcceptanceDetailInfo>> searchListById(Integer id){
		List<ProductAcceptanceDetailInfo> padiList = new ArrayList<>();
		CheckLogInfo cli = new CheckLogInfo();
		cli.setCheckId(id);
		List<CheckLogInfo> cliList = checkLogService.getListBy(cli);
		if (cliList.size()!=0){
			for (int i=0;i<cliList.size();i++){
				ProductAcceptanceDetailInfo padi = productAcceptanceDetailService.getModel(cliList.get(i).getAcceptanceDetailId());
				padi.setResult(cliList.get(i).getCheckResult());
				padiList.add(padi);
			}
		}
		return ResultUtils.success(padiList);
	}
}
