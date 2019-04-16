package com.ecms.service;

import com.ecms.bean.PurchaseOrdersListInfo;
import com.ecms.dal.entity.*;
import com.ecms.excel.PurchaseOrdersListTemplate;
import com.ecms.manager.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrdersListService extends BaseService<PurchaseOrdersListEntity, PurchaseOrdersListInfo> {

	@Resource
	private PurchaseOrdersListManager purchaseOrdersListManager;

	@Resource
	private PurchaseOrdersManager purchaseOrdersManager;

	@Override
	protected BaseManager<PurchaseOrdersListEntity> getManager() {
		return purchaseOrdersListManager;
	}

	@Transactional
	public void verifivat(PurchaseOrdersListInfo purchaseOrdersListInfo){
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		PurchaseOrdersListEntity pole = new PurchaseOrdersListEntity();
		pole.setId(purchaseOrdersListInfo.getId());
		pole.setSettlementQty(purchaseOrdersListInfo.getSettlementQty());
		pole.setActualDeliveryQty(purchaseOrdersListInfo.getActualDeliveryQty());
		pole.setVerificatDate(createDate);
		pole.setVerificatPerson(LoginFilter.getCurrentUser().getUserName());
		pole.setVerificatStatus(1);
		pole.setPurchasePrice(purchaseOrdersListInfo.getPurchasePrice());
		pole.setPurchasePriceTotal(purchaseOrdersListInfo.getPurchasePriceTotal());
		purchaseOrdersListManager.updateByPrimaryKeySelective(pole);
		//根据采购单明细id查询采购单id
		PurchaseOrdersListEntity purchaseOrdersListEntity = purchaseOrdersListManager.getEntityById(purchaseOrdersListInfo.getId());
		Integer purchaseOrdersId = purchaseOrdersListEntity.getPurchaseOrdersId();
		//根据采购单id查询采购单所有明细
		PurchaseOrdersListEntity purchaseOrdersListEntity1 = new PurchaseOrdersListEntity();
		purchaseOrdersListEntity1.setPurchaseOrdersId(purchaseOrdersId);
		purchaseOrdersListEntity1.setStatus(1);
		purchaseOrdersListEntity1.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<PurchaseOrdersListEntity> poleList = purchaseOrdersListManager.getListBy(purchaseOrdersListEntity1);
		BigDecimal total = new BigDecimal(0);
		if (poleList.size()!=0){
			for (int i=0;i<poleList.size();i++){
				total = total.add(poleList.get(i).getPurchasePriceTotal());
			}
		}
		PurchaseOrdersEntity poe = new PurchaseOrdersEntity();
		poe.setPurchasePriceTotal(total);
		poe.setId(purchaseOrdersId);
		purchaseOrdersManager.updateByPrimaryKeySelective(poe);
	}

	@Transactional
	public void change(PurchaseOrdersListInfo purchaseOrdersListInfo){
		/*PurchaseOrdersListEntity pole = new PurchaseOrdersListEntity();
		pole.setMaterialId(purchaseOrdersListInfo.getMaterialId());
		pole.setPurchaseOrdersId(purchaseOrdersListInfo.getPurchaseOrdersId());
		List<PurchaseOrdersListEntity> poleList = purchaseOrdersListManager.getListBy(pole);
		if (poleList.size()!=0){
			//如果已经核销，则变成待核销
			if (poleList.get(0).getVerificatStatus() == 1){
				pole.setVerificatStatus(0);
				pole.setId(poleList.get(0).getId());
				purchaseOrdersListManager.updateByPrimaryKeySelective(pole);
			}
		}*/
	}

	public List<PurchaseOrdersListTemplate> getExportList(){
		List<PurchaseOrdersListTemplate> result = new ArrayList<>();
		PurchaseOrdersListEntity ordersListEntity = new PurchaseOrdersListEntity();
		ordersListEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		ordersListEntity.setVerificatStatus(STATUS_INVALID);
		ordersListEntity.setStatus(STATUS_DEFAULT);
		List<PurchaseOrdersListEntity> ordersListEntityList = purchaseOrdersListManager.getListBy(ordersListEntity);
		if (ordersListEntityList.size()!=0){
			for (PurchaseOrdersListEntity listEntity : ordersListEntityList){
				PurchaseOrdersListTemplate ordersListTemplate = new PurchaseOrdersListTemplate();
				ordersListTemplate.setMaterialCode(listEntity.getMaterialCode());
				ordersListTemplate.setMaterialName(listEntity.getMaterialName());
				ordersListTemplate.setMaterialType(listEntity.getType());
				ordersListTemplate.setMaterialBomParamValueStr(listEntity.getMaterialBomParamValueStr());
				ordersListTemplate.setOriginalMaterial(listEntity.getOriginalMaterial());
				ordersListTemplate.setDrawingCode(listEntity.getDrawingCode());
				ordersListTemplate.setPurchaseQty(listEntity.getPurchaseQty());
				ordersListTemplate.setPurchaseReason(listEntity.getPurchaseReason());
				ordersListTemplate.setSupplierName(listEntity.getSupplierName());
				ordersListTemplate.setArriveDate(listEntity.getArriveDate());
				ordersListTemplate.setReceivedQty(listEntity.getReceivedQty());
				ordersListTemplate.setPurchasePriceTotal(Float.parseFloat(listEntity.getPurchasePriceTotal().toString()));
				PurchaseOrdersEntity ordersEntity = purchaseOrdersManager.getEntityById(listEntity.getPurchaseOrdersId());
				ordersListTemplate.setPurchaseDate(ordersEntity.getPurchaseDate());
				result.add(ordersListTemplate);
			}
		}
		return result;
	}
}
