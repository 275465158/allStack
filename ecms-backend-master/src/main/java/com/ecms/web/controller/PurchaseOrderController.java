package com.ecms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.PurchaseOrderInfo;
import com.ecms.bean.PurchaseQueueInfo;
import com.ecms.bean.SupplierMaterialInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchaseOrderEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialBomService;
import com.ecms.service.PurchaseOrderService;
import com.ecms.service.SupplierMaterialService;
import com.ecms.service.SupplierService;
import com.ecms.utils.CollectionEditorSupport;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("purchase/order")
public class PurchaseOrderController extends BaseController<PurchaseOrderEntity, PurchaseOrderInfo> {

	@Resource
	private PurchaseOrderService service;
	@Resource
	private SupplierMaterialService supplierMaterialService;
	@Resource
	private MaterialBomService materialBomService;
	@Resource
	private SupplierService supplierService;

	@Override
	protected BaseService<PurchaseOrderEntity, PurchaseOrderInfo> getBaseService() {
		return service;
	}

	@RequestMapping("orderByMaterial")
	public Result<Void> save(PurchaseOrderInfo modal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		modal.getQueueList().stream().forEach(queueId -> {
			// PurchaseQueueInfo queueInfo = queueService.getModel(queueId);
			PurchaseOrderInfo info = new PurchaseOrderInfo();
			// ExchangeUtils.exchangeObject(queueInfo, PurchaseOrderInfo.class);
			SupplierMaterialInfo supplierMaterial = supplierMaterialService.getBySupplierMaterial(info.getMaterialId(),
					modal.getSupplierId());
			info.setId(null);
			Calendar ca = Calendar.getInstance();
			// 增加的天数
			ca.add(Calendar.DATE, Integer.parseInt(supplierMaterial.getPurchaseDays()));

			Date currdate = ca.getTime();
			info.setMaterialId(queueId.getMaterialId());
			info.setLastestTime(format.format(currdate));
			info.setSupplierId(modal.getSupplierId());
			info.setPrice(supplierMaterial.getPrice());
			info.setQty(queueId.getOrderQty());
			info.setTotal(queueId.getOrderQty() * supplierMaterial.getPrice());
			info.setBuyer(modal.getBuyer());
			info.setOrderDate(modal.getOrderDate());
			info.setOrderNumber(modal.getOrderNumber());
			info.setPaymentDate(modal.getPaymentDate());
			info.setEstimatedDate(modal.getEstimatedDate());
			info.setRepertoryId(supplierMaterial.getRepertoryId());
			info.setReason(queueId.getReason());
			service.saveOrUpdate(info);
		});
		return ResultUtils.success();
	}

	@RequestMapping("arrived")
	public Result<Void> arrived(Integer id, Boolean arrived) {
		service.updateArrived(id, arrived);
		return ResultUtils.success();
	}

	@RequestMapping("approved")
	public Result<Void> approved(Integer id, Boolean approved) {
		service.updateApproved(id, approved);
		return ResultUtils.success();
	}

	@RequestMapping("info")
	public Result<PurchaseOrderInfo> info(Integer id) {
		PurchaseOrderInfo info = service.getModel(id);
		info.setMaterialBom(materialBomService.getMaterialBomModal(info.getMaterialId()));
        return ResultUtils.success(info);
	}

	@Override
	public Result<PurchaseOrderInfo> getById(Integer id) {
		PurchaseOrderInfo info = service.getModel(id);
		info.setMaterialBom(materialBomService.getMaterialBomModal(info.getMaterialId()));
		info.setSupplier(supplierService.getModel(info.getSupplierId()));
		return ResultUtils.success(info);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		binder.registerCustomEditor(Collection.class, "queueList",
				new CollectionEditorSupport(PurchaseQueueInfo.class));

	}
}
