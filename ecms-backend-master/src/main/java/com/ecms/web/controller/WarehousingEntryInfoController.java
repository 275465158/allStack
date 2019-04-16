package com.ecms.web.controller;

import com.ecms.bean.MaterielInfo;
import com.ecms.bean.WarehousingEntryBeanInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.WarehousingEntryInfoEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterielService;
import com.ecms.service.WarehousingEntryInfoService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("warehousingEntry")
public class WarehousingEntryInfoController extends BaseController<WarehousingEntryInfoEntity, WarehousingEntryBeanInfo> {

	@Resource
	private WarehousingEntryInfoService warehousingEntryInfoService;
	@Resource
	private MaterielService materielService;

	@Override
	protected BaseService<WarehousingEntryInfoEntity, WarehousingEntryBeanInfo> getBaseService() {
		return warehousingEntryInfoService;
	}

	@RequestMapping("insertByInfo")
	public Result<Void> insertInbound(WarehousingEntryBeanInfo warehousingEntryBeanInfo){
		warehousingEntryBeanInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date.getTime());
		warehousingEntryBeanInfo.setMaterielStockFrom(time);
		List<Map<String,Object>> moreNotifyObject = warehousingEntryBeanInfo.getMoreNotifyObject();
		if(moreNotifyObject.size() != 0){
			for(Map<String,Object> objectStr:moreNotifyObject){
				warehousingEntryBeanInfo.setQty(Float.parseFloat(objectStr.get("qty").toString()));
				warehousingEntryBeanInfo.setMaterielBatch(objectStr.get("materielBatch").toString());
				warehousingEntryBeanInfo.setShelfPosition(objectStr.get("shelfPosition").toString());
				warehousingEntryInfoService.insertBy(warehousingEntryBeanInfo);
			}
		}
		MaterielInfo materielInfo = new MaterielInfo();
		materielInfo.setId(warehousingEntryBeanInfo.getMaterialSearchId());
		List<MaterielInfo> materielInfoList = materielService.getListBy(materielInfo);
		Float onhandNum = 0f;
		if(materielInfoList.size() != 0){
			for(MaterielInfo mi :materielInfoList){
				if(mi.getOnhand() != null){
					onhandNum += mi.getOnhand();
				}
			}
		}
		materielInfo.setOnhand(warehousingEntryBeanInfo.getTotalNum()+onhandNum);
		materielService.saveOrUpdate(materielInfo);
		return ResultUtils.success();
	}

	@RequestMapping("getByMaterialId")
	public Result<Pager<WarehousingEntryBeanInfo>> getByMaterialId(Integer materialId){
		WarehousingEntryBeanInfo warehousingEntryBeanInfo = new WarehousingEntryBeanInfo();
		warehousingEntryBeanInfo.setMaterialId(materialId);
		Pager<WarehousingEntryBeanInfo> pager = warehousingEntryInfoService.getModelList(1, DEFAULT_PAGE_SIZE, warehousingEntryBeanInfo);
		return ResultUtils.success(pager);
	}

	@RequestMapping("/saveOrUpdateTable")
	public Result<WarehousingEntryBeanInfo> saveOrUpdateTable(WarehousingEntryBeanInfo warehousingEntryBeanInfo) {
		WarehousingEntryBeanInfo webi = new WarehousingEntryBeanInfo();
		List<Map<String,Object>> tableDataRow = warehousingEntryBeanInfo.getTableDataRow();
		for(Map<String,Object> tdr : tableDataRow){
			webi.setId(Integer.parseInt(tdr.get("id").toString()));
			webi.setQty(Float.parseFloat(tdr.get("qty").toString()) - Float.parseFloat(tdr.get("outBoundNum").toString()));
			warehousingEntryInfoService.updateByPrimaryKeySelective(webi);
		}
		return ResultUtils.success();
	}

}
