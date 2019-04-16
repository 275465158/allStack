package com.ecms.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import com.ecms.bean.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.common.Result;
import com.ecms.dal.entity.PurchaseQueueEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialBomService;
import com.ecms.service.MaterielInventoryService;
import com.ecms.service.MaterielService;
import com.ecms.service.PurchaseQueueService;
import com.ecms.service.SellerOrderService;
import com.ecms.service.SupplierMaterialService;
import com.ecms.service.WarehousingEntryService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.MaterialBomEditor;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("purchase/queue")
public class PurchaseQueueController extends BaseController<PurchaseQueueEntity, PurchaseQueueInfo> {

	@Resource
	private PurchaseQueueService service;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private SupplierMaterialService supplierMaterialService;
	@Resource
	private SellerOrderService sellerOrderService;
	@Resource
	private WarehousingEntryService warehousingEntryService;
	@Resource
	private MaterielInventoryService inventoryService;
	@Resource
	private MaterielService materielService;

	@Override
	protected BaseService<PurchaseQueueEntity, PurchaseQueueInfo> getBaseService() {
		return service;
	}

	/*@Override
	@RequestMapping("list")
	public Result<Pager<PurchaseQueueInfo>> list(int pageNum, PurchaseQueueInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		List<PurchaseQueueInfo> list = new ArrayList<PurchaseQueueInfo>();
		// 所有待生产的销售产品记录
		Map<Integer, IntSummaryStatistics> materialList = new HashMap<Integer, IntSummaryStatistics>();
		// 初始化map
		SellerOrderInfo searchOrder = new SellerOrderInfo();
		searchOrder.setProgress(SellerOrderProgress.WAIT_PRODUCE.getId());
		List<SellerOrderInfo> orderInfoList = sellerOrderService.getListBy(searchOrder);
		for (SellerOrderInfo orderInfo : orderInfoList) {
			SellerOrderIndex orderIndex = sellerOrderService.getIndexById(orderInfo.getId());
			List<SellerOrderProductInfo> orderProductList = orderIndex.getProductInfos();
			materialList = orderProductList.stream().collect(Collectors.groupingBy(SellerOrderProductInfo::getProductId,
					Collectors.summarizingInt(SellerOrderProductInfo::getProductNumber)));

			for (Map.Entry<Integer, IntSummaryStatistics> entry : materialList.entrySet()) {
				Map<Integer, Float> needPurchaseList = new HashMap<Integer, Float>();
				MaterialBomModal materialBom = bomService.getMaterialBomModal(entry.getKey());
				List<MaterialPartInfo> partListLevelOneByPurchase = materialBom.getMaterialParts().stream()
						.filter(d -> d.getPurchaseType().equals(MaterielPartPurchaseType.FROM_PURCHASE.getId()))
						.collect(Collectors.toList());
				List<MaterialPartInfo> partListLevelOneByProduce = materialBom.getMaterialParts().stream()
						.filter(d -> d.getPurchaseType().equals(MaterielPartPurchaseType.SELFMAKE.getId()))
						.collect(Collectors.toList());
				for (MaterialPartInfo partInfo : partListLevelOneByPurchase) {
					float needNumber = partInfo.getConsumeQty() * entry.getValue().getSum();
					if (needPurchaseList.containsKey(partInfo.getMaterialBomId())) {
						needPurchaseList.put(partInfo.getMaterialBomId(),
								needPurchaseList.get(partInfo.getMaterialBomId()) + needNumber);
					} else {
						needPurchaseList.put(partInfo.getMaterialBomId(), needNumber);
					}
				}
				for (MaterialPartInfo partInfo : partListLevelOneByProduce) {
					MaterialBomModal partBom = bomService.getMaterialBomModal(partInfo.getMaterialBomId());
					List<MaterialPartInfo> partListLevelTwoByPurchase = partBom.getMaterialParts().stream()
							.filter(d -> d.getPurchaseType().equals(MaterielPartPurchaseType.FROM_PURCHASE.getId()))
							.collect(Collectors.toList());
					for (MaterialPartInfo partInfolv2 : partListLevelTwoByPurchase) {
						float needNumber = partInfolv2.getConsumeQty() * entry.getValue().getSum()
								* partInfo.getConsumeQty();
						if (needPurchaseList.containsKey(partInfolv2.getMaterialBomId())) {
							needPurchaseList.put(partInfolv2.getMaterialBomId(),
									needPurchaseList.get(partInfolv2.getMaterialBomId()) + needNumber);
						} else {
							needPurchaseList.put(partInfolv2.getMaterialBomId(), needNumber);
						}
					}
				}

				for (Map.Entry<Integer, Float> needPurchaseEntry : needPurchaseList.entrySet()) {
					PurchaseQueueInfo queueInfo = new PurchaseQueueInfo();
					queueInfo.setReason(orderIndex.getOrderInfo().getContractId());
					queueInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
					queueInfo.setMaterialBom(bomService.getModel(needPurchaseEntry.getKey()));
					queueInfo.setMaterialId(needPurchaseEntry.getKey());
					// 获取物料的采购在途的数量
					WarehousingEntryMaterialModal searchentryInfo = new WarehousingEntryMaterialModal();
					searchentryInfo.setMaterialId(needPurchaseEntry.getKey());
					List<WarehousingEntryMaterialModal> entryList = warehousingEntryService.getListBy(searchentryInfo)
							.stream().filter(d -> d.getEntryTotalQty() < d.getQty()).collect(Collectors.toList());
					float purchasingQty = entryList.stream().map(WarehousingEntryMaterialModal::getQty).reduce(0.0f,
							Float::sum);
					// 获取当前物料在裤情况
					MaterielInfo materielInfo = materielService.getByMaterialId(needPurchaseEntry.getKey());
					MaterielInventoryInfo searchInventory = new MaterielInventoryInfo();
					if(materielInfo.getId() != null){
						searchInventory.setMaterielId(materielInfo.getId());
					}
					List<MaterielInventoryInfo> inventoryDetail = inventoryService.getListBy(searchInventory).stream()
							.filter(d -> d.getQty() > 0).collect(Collectors.toList());
					float inventoryQty = inventoryDetail.stream().map(MaterielInventoryInfo::getQty).reduce(0.0f,
							Float::sum);

					float finalQty = needPurchaseEntry.getValue() - purchasingQty - inventoryQty;
					queueInfo.setQty(finalQty);
					queueInfo.setOrderQty(finalQty);
					if (finalQty >= 0) {
						list.add(queueInfo);
					}
				}
			}
		}
		Pager<PurchaseQueueInfo> pager = new Pager<PurchaseQueueInfo>(pageNum, DEFAULT_PAGE_SIZE, list.size(), list);
		// service.getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
		pager.getList().stream().forEach(queueInfo -> {
			MaterialBomModal bomModal = bomService.getMaterialBomModal(queueInfo.getMaterialId());
			queueInfo.setMaterialBom(bomModal);
		});
		return ResultUtils.success(pager);
	}*/

	@RequestMapping("listBySupplierMaterial")
	public Result<List<PurchaseQueueInfo>> listBySupplierMaterial(int pageNum, PurchaseQueueInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		PurchaseQueueInfo selectionModal = service.getModel(info.getId());
		List<SupplierMaterialInfo> listSupplierMaterial = supplierMaterialService
				.getByMaterialId(selectionModal.getMaterialId());
		List<PurchaseQueueInfo> listPurchaseQueue = new ArrayList<PurchaseQueueInfo>();
		listSupplierMaterial.stream().forEach(supplierMaterial -> {
			PurchaseQueueInfo result = service.getModalByMaterialId(supplierMaterial.getMaterialId());
			MaterialBomModal bomModal = bomService.getMaterialBomModal(result.getMaterialId());
			result.setMaterialBom(bomModal);
			listPurchaseQueue.add(result);
		});
		return ResultUtils.success(listPurchaseQueue);
	}

	@RequestMapping("save")
	public Result<Void> save(PurchaseQueueInfo info) {
		service.saveOrUpdate(info);
		return ResultUtils.success();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		MaterialBomEditor materialBomEditor = new MaterialBomEditor();
		binder.registerCustomEditor(MaterialBomInfo.class, "materialBom", materialBomEditor);
	}

	/**
	 * 获取待采购列表
	 * @param pageNum
	 * @param pageSize
	 * @param purchaseQueueInfo
	 * @return
	 *//*
	@RequestMapping("/purchaseList")
	public Result<Pager<PurchaseQueueInfo>> purchaseList(int pageNum,int pageSize,PurchaseQueueInfo purchaseQueueInfo){
		Pager<PurchaseQueueInfo> pager = service.getModelListByLike(pageNum,pageSize, purchaseQueueInfo, false);
		if (pager.getList().size()!=0){
			for (int i=0;i<pager.getList().size();i++){
				Integer materialId = pager.getList().get(i).getMaterialId();
				MaterialBomInfo mbi = bomService.getModel(materialId);
				pager.getList().get(i).setMaterialBom(mbi);
			}
		}
		return ResultUtils.success(pager);
	}*/
}
