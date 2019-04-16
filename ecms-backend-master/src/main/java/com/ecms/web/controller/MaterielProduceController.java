/**
 * 
 */
package com.ecms.web.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterialPartInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.bean.MaterielProduceDetailInfo;
import com.ecms.bean.MaterielProduceInfo;
import com.ecms.bean.ProductionPlanInfo;
import com.ecms.common.MaterielStatus;
import com.ecms.common.Pager;
import com.ecms.common.PickingStatus;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterielProduceEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialBomService;
import com.ecms.service.MaterielInventoryService;
import com.ecms.service.MaterielProduceDetailService;
import com.ecms.service.MaterielProduceService;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;

/**
 * @author alan.wu 2018年8月25日
 * @Description 生产发料
 */
@RestController
@RequestMapping("materiel/produce")
public class MaterielProduceController extends BaseController<MaterielProduceEntity, MaterielProduceInfo> {

	@Resource
	private MaterielProduceService service;
	@Resource
	private MaterielProduceDetailService detailService;
	@Resource
	private ProductionPlanService productionPlanService;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterielService materielService;
	@Resource
	private MaterielInventoryService inventoryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecms.web.controller.BaseController#getBaseService()
	 */
	@Override
	protected BaseService<MaterielProduceEntity, MaterielProduceInfo> getBaseService() {
		return service;
	}

	@Override
	public Result<Pager<MaterielProduceInfo>> list(int pageNum, MaterielProduceInfo info) {
		List<MaterielProduceInfo> list = new ArrayList<MaterielProduceInfo>();
		ProductionPlanInfo searchPlanInfo = new ProductionPlanInfo();
		List<ProductionPlanInfo> planList = productionPlanService.getListBy(searchPlanInfo);
		for (ProductionPlanInfo planInfo : planList) {
			Boolean materialStatus = true;
			Map<Integer, Float> pickList = new HashMap<Integer, Float>();
			MaterialBomModal bomModal = bomService.getMaterialBomModal(Integer.valueOf(planInfo.getMaterialId()));
			List<MaterialPartInfo> partListLevelOneByPurchase = bomModal.getMaterialParts();
			for (MaterialPartInfo partInfo : partListLevelOneByPurchase) {
				float pickNumber = partInfo.getConsumeQty() * planInfo.getMaterialNum();
				if (pickList.containsKey(partInfo.getMaterialBomId())) {
					pickList.put(partInfo.getMaterialBomId(), pickList.get(partInfo.getMaterialBomId()) + pickNumber);
				} else {
					pickList.put(partInfo.getMaterialBomId(), pickNumber);
				}
				MaterielInfo materielInfo = materielService.getByMaterialId(partInfo.getMaterialBomId());
				if (materielInfo != null) {
					MaterielInventoryInfo searchInventory = new MaterielInventoryInfo();
					searchInventory.setMaterielId(materielInfo.getId());
					List<MaterielInventoryInfo> inventoryDetail = inventoryService.getListBy(searchInventory).stream()
							.filter(d -> d.getQty() > 0).collect(Collectors.toList());
					float inventoryQty = inventoryDetail.stream().map(MaterielInventoryInfo::getQty).reduce(0.0f,
							Float::sum);
					if (pickNumber > inventoryQty) {
						materialStatus = false;
					}
				} else {
					materialStatus = false;
				}
			}
			MaterielProduceInfo materielProduceInfo = new MaterielProduceInfo(),
					searchProduceInfo = new MaterielProduceInfo();
			materielProduceInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			materielProduceInfo.setMaterielStatus(
					materialStatus ? MaterielStatus.READY.getId().toString() : MaterielStatus.WAIT.getId().toString());
			materielProduceInfo.setProductPlanId(planInfo.getId());
			materielProduceInfo.setProductionPlan(planInfo);
			materielProduceInfo.setRepertoryId(info.getRepertoryId());
			materielProduceInfo.setPickStatus(PickingStatus.WAITING.getId().toString());
			searchProduceInfo.setProductPlanId(planInfo.getId());
			List<MaterielProduceInfo> result = service.getListBy(searchProduceInfo);
			if (result.size() <= 0) {
				materielProduceInfo = service.saveOrUpdate(materielProduceInfo);
			} else {
				materielProduceInfo = result.get(0);
			}
			for (Entry<Integer, Float> entry : pickList.entrySet()) {
				MaterielProduceDetailInfo detailInfo = new MaterielProduceDetailInfo();
				detailInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				detailInfo.setMaterialId(entry.getKey());
				detailInfo.setProduceId(materielProduceInfo.getId());
				detailInfo.setPickQty(entry.getValue());
				List<MaterielProduceDetailInfo> resDetail = detailService.getListBy(detailInfo);
				if (resDetail.size() <= 0) {
					detailService.saveOrUpdate(detailInfo);
				}
			}
			// list.add(materielProduceInfo);
		}
		Pager<MaterielProduceInfo> pager = service.getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
		pager.getList().stream().forEach(mpInfo -> {
			mpInfo.setProductionPlan(productionPlanService.getModel(mpInfo.getProductPlanId()));
		});
		return ResultUtils.success(pager);
	}

	@RequestMapping("info")
	public Result<MaterielProduceInfo> info(Integer id) {
		MaterielProduceInfo info = service.getModel(id);
		MaterielProduceDetailInfo searchDetail = new MaterielProduceDetailInfo();
		searchDetail.setProduceId(info.getId());
		List<MaterielProduceDetailInfo> detailList = detailService.getListBy(searchDetail);
		info.setDetailList(detailList);
		return ResultUtils.success(info);
	}

	@RequestMapping("picking")
	public Result<MaterielProduceInfo> picking(Integer id) {
		MaterielProduceInfo info = service.getModel(id);
		info.setPickStatus(PickingStatus.COMPLETED.getId().toString());
		service.saveOrUpdate(info);
		MaterielProduceDetailInfo searchDetail = new MaterielProduceDetailInfo();
		searchDetail.setProduceId(info.getId());
		List<MaterielProduceDetailInfo> detailList = detailService.getListBy(searchDetail);
		for (MaterielProduceDetailInfo detailInfo : detailList) {
			Float needQty = detailInfo.getPickQty();
			MaterielInfo materielInfo = materielService.getByMaterialId(detailInfo.getMaterialId());
			if (materielInfo != null) {
				List<MaterielInventoryInfo> inventoryList = materielInfo.getInventoryList().stream()
						.filter(d->{return d.getQty()>0.0f;})
						.sorted(Comparator.comparing(MaterielInventoryInfo::getId)).collect(Collectors.toList());
				Iterator<MaterielInventoryInfo> iter = inventoryList.iterator();
				while (needQty > 0 && iter.hasNext()) {
					MaterielInventoryInfo inventoryInfo = iter.next();
					if (inventoryInfo.getQty() >= needQty) {
						inventoryInfo.setQty(inventoryInfo.getQty() - needQty);
						needQty = 0.0f;
					} else {
						needQty = needQty - inventoryInfo.getQty();
						inventoryInfo.setQty(0.0f);		
					}
					System.out.println("after"+needQty+",inventory id:"+inventoryInfo.getId()+" Qty:"+inventoryInfo.getQty());
					inventoryService.saveOrUpdate(inventoryInfo);
				}
			}
		}
		return ResultUtils.success();
	}
}
