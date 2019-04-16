package com.ecms.service;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.PurchaseOrdersStayInfo;
import com.ecms.bean.SupplierInfo;
import com.ecms.dal.entity.*;
import com.ecms.excel.PurchaseOrdersStayTemplate;
import com.ecms.manager.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

@Service
public class PurchaseOrdersStayService extends BaseService<PurchaseOrdersStayEntity, PurchaseOrdersStayInfo> {

	@Resource
	private PurchaseOrdersStayManager purchaseOrdersStayManager;

	@Resource
	private MaterialRepertoryManager materialRepertoryManager;

	@Resource
	private SupplierManager supplierManager;

	@Resource
	private MaterialBomManager materialBomManager;

	@Override
	protected BaseManager<PurchaseOrdersStayEntity> getManager() {
		return purchaseOrdersStayManager;
	}

	@Override
	protected String getRedisNumberKeyPrefix() {
		return "number_purchase_stay_";
	}

	@Transactional
	public void internalLoss(PurchaseOrdersStayInfo purchaseOrdersStayInfo){
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		//计算物料需求够不够，不够生成待采购单
		//总库存数
		Float inventoryQty = 0.00f;
		//订单需求数
		Float orderNeedQty = 0.00f;
		//待入库总数
		Float stayInboundQty = 0.00f;
		//待收货总数
		Float stayDeliveredQty = 0.00f;
		//待采购总数
		Float stayPurchaseQty = 0.00f;
		MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
		mre.setMaterialId(purchaseOrdersStayInfo.getMaterialId());
		List<MaterialRepertoryEntity> mreList =  materialRepertoryManager.getListBy(mre);
		if (mreList.size()!=0){
			inventoryQty = mreList.get(0).getInventoryQty();
			stayDeliveredQty = mreList.get(0).getStayDeliveredQty();
			stayInboundQty = mreList.get(0).getStayInboundQty();
			orderNeedQty = mreList.get(0).getOrderNeedQty();
			stayPurchaseQty = mreList.get(0).getStayPurchaseQty();
			//算出当前物料可用的数量
			Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty-orderNeedQty;
			if (differenceQty<0){
				//则说明物料不足，需要重新购买
				PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
				pose.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				pose.setPurchaseStayStatus(0);
				pose.setStayPurQty(-differenceQty);
				pose.setLatestPurDate(createDate);
				pose.setPurchaseReason("不合格内部消耗需重新采购");
				pose.setOrderId(0);
				PurchaseOrdersStayEntity purchaseOrdersStayEntity = purchaseOrdersStayManager.insertBy(pose);
				String purchaseStayCode = "Pur" +frontCompWithZore(purchaseOrdersStayEntity.getId(),8);
				purchaseOrdersStayEntity.setPurchaseStayCode(purchaseStayCode);
				purchaseOrdersStayManager.updateByPrimaryKeySelective(purchaseOrdersStayEntity);
				//最后更新仓库带采购数
				mre.setId(mreList.get(0).getId());
				mre.setStayPurchaseQty(mreList.get(0).getStayPurchaseQty()+(-differenceQty));
				materialRepertoryManager.updateByPrimaryKeySelective(mre);
			}
		}
	}

	@Transactional
	public void saveSupplierId(Integer purchaseOrdersId,Integer supplierId){
		PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
		pose.setId(purchaseOrdersId);
		pose.setSupplierId(supplierId);
		SupplierEntity se = supplierManager.getEntityById(supplierId);
		pose.setSupplierName(se.getSupplierName());
		purchaseOrdersStayManager.updateByPrimaryKeySelective(pose);
	}

	public List<PurchaseOrdersStayTemplate> getExportList(){
		List<PurchaseOrdersStayTemplate> result = new ArrayList<>();
		PurchaseOrdersStayEntity stayEntity = new PurchaseOrdersStayEntity();
		stayEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		stayEntity.setStatus(STATUS_DEFAULT);
		stayEntity.setPurchaseStayStatus(STATUS_INVALID);
		List<PurchaseOrdersStayEntity> stayEntityList = purchaseOrdersStayManager.getListBy(stayEntity);
		if (stayEntityList.size()!=0){
			for (PurchaseOrdersStayEntity ordersStayEntity : stayEntityList){
				PurchaseOrdersStayTemplate stayTemplate = new PurchaseOrdersStayTemplate();
				stayTemplate.setLatestPurDate(ordersStayEntity.getLatestPurDate());
				stayTemplate.setMaterialBomParamValueStr(ordersStayEntity.getMaterialBomParamValueStr());
				stayTemplate.setMaterialCode(ordersStayEntity.getMaterialCode());
				stayTemplate.setMaterialName(ordersStayEntity.getMaterialName());
				stayTemplate.setMaterialType(ordersStayEntity.getMaterialType());
				stayTemplate.setOriginalMaterial(ordersStayEntity.getOriginalMaterial());
				stayTemplate.setPurchaseReason(ordersStayEntity.getPurchaseReason());
				stayTemplate.setStayPurQty(ordersStayEntity.getStayPurQty());
				stayTemplate.setSupplierName(ordersStayEntity.getSupplierName());
				MaterialBomEntity mbe = materialBomManager.getEntityById(ordersStayEntity.getMaterialId());
				stayTemplate.setDrawingCode(mbe.getDrawingCode());
				result.add(stayTemplate);
			}
		}
		return result;
	}

}
