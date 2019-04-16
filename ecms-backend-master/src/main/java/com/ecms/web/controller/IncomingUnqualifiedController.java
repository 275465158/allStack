package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.IncomingDisposalMeasure;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.IncomingUnqualifiedEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("incomingUnqualified")
public class IncomingUnqualifiedController extends BaseController<IncomingUnqualifiedEntity, IncomingUnqualifiedInfo> {

	@Resource
	private IncomingUnqualifiedService incomingUnqualifiedService;

	@Resource
	private MaterialBomService materialBomService;

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Resource
	private MaterialRepertoryIncomingCheckService materialRepertoryIncomingCheckService;

	@Resource
	private EmployeeService employeeService;

	@Resource
	private PurchaseOrdersService purchaseOrdersService;

	@Override
	protected BaseService<IncomingUnqualifiedEntity, IncomingUnqualifiedInfo> getBaseService() {
		return incomingUnqualifiedService;
	}

	@RequestMapping("listMeasure")
	public Result<String> listMeasure(){
		JSONArray list = new JSONArray();
		for(IncomingDisposalMeasure measure :IncomingDisposalMeasure.values()){
			JSONObject obj = new JSONObject();
			obj.element("id",measure.getId());
			obj.element("name", measure.getName());
			list.add(obj);

		}
		return ResultUtils.success(list.toString());
	}

	@RequestMapping("searchPager")
	public Result<Pager<IncomingUnqualifiedInfo>> searchPager(int pageNum,int pageSize,IncomingUnqualifiedInfo incomingUnqualifiedInfo){
		Pager<IncomingUnqualifiedInfo> pager = incomingUnqualifiedService.getModelListByLike(pageNum,pageSize,incomingUnqualifiedInfo,false);
		if (pager.getList().size()!=0){
			List<IncomingUnqualifiedInfo> list = pager.getList();
			for (int i=0;i<list.size();i++){
				Integer materialId = list.get(i).getMaterialId();
				MaterialBomInfo mbi = materialBomService.getModel(materialId);
				list.get(i).setOriginalMaterial(mbi.getOriginalMaterial());
				list.get(i).setMaterialBomParamValueStr(getParamValue(materialId));
				list.get(i).setMaterialCode(mbi.getMaterialCode());
				MaterialRepertoryIncomingCheckInfo mrici = materialRepertoryIncomingCheckService.getModel(list.get(i).getCheckId());
				list.get(i).setMaterialRepertoryIncomingCheckInfo(mrici);
				list.get(i).setCheckPerson(employeeService.getModel(mrici.getOperatingPersonnelId()).getName());
				String purchaseCode = list.get(i).getPurchaseCode();
				PurchaseOrdersInfo poi = new PurchaseOrdersInfo();
				poi.setPurchaseCode(purchaseCode);
				List<PurchaseOrdersInfo> poiList = purchaseOrdersService.getListBy(poi);
				if (poiList.size()!=0){
					list.get(i).setPurchasePerson(poiList.get(0).getPurchasePerson());
				}
				if (list.get(i).getChangedMaterialId()!=null){
					list.get(i).setChangeMaterialBomInfo(materialBomService.getModel(list.get(i).getChangedMaterialId()));
				}
			}
		}
		return ResultUtils.success(pager);
	}

	/***
	 * 不合格评审-保存（类似保存草稿）
	 * @param incomingUnqualifiedInfo
	 * @return
	 */
	@RequestMapping("/save")
	public Result<Void> save(IncomingUnqualifiedInfo incomingUnqualifiedInfo){
		incomingUnqualifiedService.saveOrUpdate(incomingUnqualifiedInfo);
		return ResultUtils.success();
	}

	/***
	 * 不合格评审-执行处理
	 * @param info
	 * @return
	 */
	@RequestMapping("executeMeasure")
	public Result<Void> executeMeasure(IncomingUnqualifiedInfo info){
		ExchangeUtils.convertBlank2Null(info);
		if(info.getEditable()==null||info.getEditable()==0) {
			info.setEditable(1);
			getBaseService().saveOrUpdate(info);
		}
		IncomingDisposalMeasure measure = IncomingDisposalMeasure.getById(info.getDisposalHandleId());
		switch (measure){
			case RETURN:
				returnMaterial(info);
				break;
			case EXCHANGE:
				exchange(info);
				break;
			case CONCESSIONS:
				concessions(info);
				break;
			case INTERNALLOSS:
				internalloss(info);
				break;
			case CHANGED:
				changed(info);
				break;
		}
		return ResultUtils.success();
	}

	/***
	 * 不合格评审-退货
	 * @param info
	 */
	public void returnMaterial(IncomingUnqualifiedInfo info){
		incomingUnqualifiedService.returnMaterial(info);
	}

	/***
	 * 不合格评审-换货
	 * @param info
	 */
	public void exchange(IncomingUnqualifiedInfo info){
		incomingUnqualifiedService.exchange(info);
	}

	/***
	 * 不合格评审-让步回用
	 * @param info
	 */
	public void concessions(IncomingUnqualifiedInfo info){
		incomingUnqualifiedService.concessions(info);
	}

	/***
	 * 不合格评审-内部损耗
	 * @param info
	 */
	public void internalloss(IncomingUnqualifiedInfo info){
		incomingUnqualifiedService.internalloss(info);
	}

	/***
	 * 不合格评审-物料转换
	 * @param info
	 */
	public void changed(IncomingUnqualifiedInfo info){
		incomingUnqualifiedService.changed(info);
	}


	/**
	 * 获取拼接的参数值
	 * @param id
	 * @return
	 */
	public String getParamValue(Integer id){
		//根据子id去查部件-参数表
		MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
		materialBomParamNameInfo.setMaterialId(id);
		materialBomParamNameInfo.setStatus(1);
		List<MaterialBomParamNameInfo> mbpniList = materialBomParamNameService.getListBy(materialBomParamNameInfo);
		String materialBomParamNameValueStr = "";
		if (mbpniList.size()!=0){
			for (int j = 0;j < mbpniList.size(); j++){
				if (j<mbpniList.size()-1) {
					materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
				}else {
					materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
				}
			}
		}
		if (materialBomParamNameValueStr != ""){
			return (materialBomParamNameValueStr);
		}else {
			return ("");
		}
	}

}
