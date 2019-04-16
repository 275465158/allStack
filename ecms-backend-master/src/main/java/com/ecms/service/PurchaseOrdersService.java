package com.ecms.service;

import com.ecms.bean.*;
import com.ecms.common.BusinessConstants;
import com.ecms.common.Result;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrdersService extends BaseService<PurchaseOrdersEntity, PurchaseOrdersInfo> {

	@Resource
	private PurchaseOrdersManager purchaseOrdersManager;
	@Resource
	private PurchaseOrdersStayManager purchaseOrdersStayManager;
	@Resource
	private MaterialRepertoryManager materialRepertoryManager;
	@Resource
	private MaterialRepertoryDetailManager materialRepertoryDetailManager;
	@Resource
	private PurchaseOrdersListManager purchaseOrdersListManager;
	@Resource
	private PurchaseOrdersStayDetailManager purchaseOrdersStayDetailManager;
	@Resource
	private MaterialBomManager materialBomManager;
	@Resource
	private MaterialRepertoryDetailService materialRepertoryDetailService;
	@Resource
	private SupplierManager supplierManager;
	@Resource
	private ActivitiModelService activitiModelService;

	@Override
	protected BaseManager<PurchaseOrdersEntity> getManager() {
		return purchaseOrdersManager;
	}

	@Override
	protected String getRedisNumberKeyPrefix() {
		return "number_purchase_";
	}

	/**
	 * 判断当前流程是否存在
	 * @return
	 */
	public Boolean workflowIsSuspended(){
		Map<String, Object> workflowSuspended = activitiModelService.queryModelByWorkflowKey(WORKFLOW_PURCHASE_ORDER);
		boolean status = null==workflowSuspended.get("isSuspended")||(Boolean)workflowSuspended.get("isSuspended");
		return status;
	}

	public void saveIncomingCheckout(PurchaseOrdersInfo purchaseOrdersInfo){
		purchaseOrdersInfo.setInboundStatus(STAY_INBOUND_STATUS);
		PurchaseOrdersEntity purchaseOrdersEntity = ExchangeUtils.exchangeObject(purchaseOrdersInfo, PurchaseOrdersEntity.class);
		purchaseOrdersManager.updateByPrimaryKeySelective(purchaseOrdersEntity);
	}

	@Transactional
	public void savePurchaseOrders(PurchaseOrdersInfo purchaseOrdersInfo) throws ParseException {
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		//下单的时候判断这个仓库下面有没有这个物料
		MaterialRepertoryDetailEntity mrde = new MaterialRepertoryDetailEntity();
		mrde.setMaterialId(purchaseOrdersInfo.getMaterialId());
		mrde.setRepertoryId(purchaseOrdersInfo.getRepertoryId());
		MaterialBomEntity mbe = materialBomManager.getEntityById(purchaseOrdersInfo.getMaterialId());
		String numberValue = null;
		numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(),numberValue);
		List<MaterialRepertoryDetailEntity> mrdeList = materialRepertoryDetailManager.getListBy(mrde);
		//如果没有则新增一条初始数据
		if (mrdeList.size()==0){
			mrde.setInventoryQty(0f);
			mrde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			mrde.setMaterialCode(purchaseOrdersInfo.getMaterialCode());
			mrde.setMaterialName(purchaseOrdersInfo.getMaterialName());
			mrde.setOperatingPersonnelId(LoginFilter.getCurrentUser().getId());
			mrde.setInboundDate(createDate);
			mrde.setMaterialBatch(null);
			mrde.setPurchasePrice(purchaseOrdersInfo.getPurchasePrice());
			mrde.setStorageLocation(null);
			mrde.setSupplierId(purchaseOrdersInfo.getSupplierId());
			mrde.setPurchaseReason(purchaseOrdersInfo.getPurchaseReason());
			materialRepertoryDetailManager.insertBy(mrde);
		}
		//查询待采购原来的待采购数量
		PurchaseOrdersStayEntity purchaseOrdersStayInfo = new PurchaseOrdersStayEntity();
		purchaseOrdersStayInfo.setPurchaseStayCode(purchaseOrdersInfo.getPurchaseCode());
		List<PurchaseOrdersStayEntity> list = purchaseOrdersStayManager.getListBy(purchaseOrdersStayInfo);
		Float oldPurchaseQty = list.get(0).getStayPurQty();
		//获取当前待采购单id
		Integer id = list.get(0).getId();
		//先把页面传过来的值封装到采购订单实体里面
		PurchaseOrdersEntity poi = new PurchaseOrdersEntity();
		//poi.setPurchaseCode(purchaseOrdersInfo.getPurchaseCode());
		poi.setPurchaseOrdersStayId(purchaseOrdersInfo.getPurchaseOrdersStayId());
		//入库状态 0：待收货，1：待检测，2：待入库，3：已入库
		poi.setInboundStatus(0);
		poi.setFlag(0);
		poi.setRepertoryId(purchaseOrdersInfo.getRepertoryId());
		poi.setSupplierId(purchaseOrdersInfo.getSupplierId());
		poi.setPurchasePriceTotal(purchaseOrdersInfo.getPurchasePriceTotal());
		Double purchaseDays = 0.0;
		if (purchaseOrdersInfo.getPurchaseDays()!=null){
			purchaseDays = purchaseOrdersInfo.getPurchaseDays();
		}else {
			purchaseDays=1.0;
		}
		String arriveDate = getArriveDate(purchaseDays);
		poi.setArriveDate(arriveDate);
		poi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		poi.setPurchasePerson(LoginFilter.getCurrentUser().getUserName());
		poi.setPurchaseReason(purchaseOrdersInfo.getPurchaseReason());
		poi.setPurchaseDate(createDate);
		PurchaseOrdersEntity purchaseOrdersEntity = purchaseOrdersManager.insertBy(poi);
		String pruchaseCode = "Pur" +frontCompWithZore(purchaseOrdersEntity.getId(),8);
		purchaseOrdersEntity.setPurchaseCode(pruchaseCode);
		purchaseOrdersManager.updateByPrimaryKeySelective(purchaseOrdersEntity);
		//往采购明细里面新增数据
		PurchaseOrdersListEntity pole = new PurchaseOrdersListEntity();
		pole.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
		pole.setNumber(numberValue);
		pole.setMaterialSource(mbe.getSource());
		pole.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
		pole.setOriginalMaterial(mbe.getOriginalMaterial());
		pole.setDrawingCode(mbe.getDrawingCode());
		pole.setPurchaseOrdersId(purchaseOrdersEntity.getId());
		pole.setMaterialId(purchaseOrdersInfo.getMaterialId());
		pole.setMaterialCode(purchaseOrdersInfo.getMaterialCode());
		pole.setMaterialName(purchaseOrdersInfo.getMaterialName());
		pole.setUnReceivedQty(purchaseOrdersInfo.getStayPurQty());
		pole.setReceivedQty(0f);
		pole.setStayPurQty(purchaseOrdersInfo.getStayPurQty());
		pole.setPurchaseQty(purchaseOrdersInfo.getStayPurQty());
		pole.setPurchasePrice(purchaseOrdersInfo.getPurchasePrice());
		pole.setPurchasePriceTotal(purchaseOrdersInfo.getPurchasePriceTotal());
		/*pole.setAccountPerson(LoginFilter.getCurrentUser().getUserName());
		pole.setAccountDate(createDate);*/
		pole.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		pole.setPaymentMethod(purchaseOrdersInfo.getPaymentMethod());
		pole.setRepertoryId(purchaseOrdersInfo.getRepertoryId());
		pole.setSupplierId(purchaseOrdersInfo.getSupplierId());
		purchaseOrdersListManager.insertBy(pole);
		//修改仓库物料里面的数据
		MaterialRepertoryEntity mri = new MaterialRepertoryEntity();
		mri.setMaterialId(purchaseOrdersInfo.getMaterialId());
		List<MaterialRepertoryEntity> mriList = materialRepertoryManager.getListBy(mri);
		if (mriList.size()!=0){
			mri.setId(mriList.get(0).getId());
			Float stayPurQty = oldPurchaseQty-purchaseOrdersInfo.getStayPurQty();
			if (stayPurQty<=0){
				if (mriList.get(0).getStayPurchaseQty()-purchaseOrdersInfo.getStayPurQty()<=0){
					mri.setStayPurchaseQty(0f);
				}else {
					mri.setStayPurchaseQty(mriList.get(0).getStayPurchaseQty()-purchaseOrdersInfo.getStayPurQty());
				}
				//如果是小于等于0，则表示多买了或者把所有的待采购单都一次买好了，要去把多出来的部分平分给其他相同物料的待采购单
				PurchaseOrdersStayEntity posi = new PurchaseOrdersStayEntity();
				posi.setMaterialId(purchaseOrdersInfo.getMaterialId());
				posi.setPurchaseStayStatus(0);
				List<PurchaseOrdersStayEntity> posiList = purchaseOrdersStayManager.getListBy(posi);
				if (posiList.size()!=0) {
					for (int i = 0; i < posiList.size(); i++) {
						if (id==posiList.get(i).getId()){
							continue;
						}
						if (stayPurQty<0){
							stayPurQty = -stayPurQty-posiList.get(i).getStayPurQty();
						}else {
							stayPurQty = stayPurQty-posiList.get(i).getStayPurQty();
						}

						//算出多余的剪掉循环出来的数量
						if (stayPurQty>=0){
							//如果差值大于等于0，则更新待采购单状态为1（相当于已下单） PurchaseStayStatus 0未下单 1已下单
							PurchaseOrdersStayEntity posi1 = new PurchaseOrdersStayEntity();
							posi1.setId(posiList.get(i).getId());
							posi1.setPurchaseStayStatus(1);
							purchaseOrdersStayManager.updateByPrimaryKeySelective(posi1);
						}else{
							//如果小于0，则说明差值就是还缺少的数量，然后把这个数量更新到这个待采购单
							PurchaseOrdersStayEntity posi2 = new PurchaseOrdersStayEntity();
							posi2.setId(posiList.get(i).getId());
							posi2.setStayPurQty(-stayPurQty);
							purchaseOrdersStayManager.updateByPrimaryKeySelective(posi2);
							break;
						}
					}
				}
				//修改待采购单状态为已下单
				PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
				pose.setId(id);
				//0未下单 1已下单
				pose.setPurchaseStayStatus(1);
				purchaseOrdersStayManager.updateByPrimaryKeySelective(pose);
			}else {
				//仓库带采购总数
				mri.setStayPurchaseQty(stayPurQty);
				//当前待采购单保留，修改待采购数量为stayPurQty
				purchaseOrdersStayInfo.setId(id);
				purchaseOrdersStayInfo.setStayPurQty(stayPurQty);
				purchaseOrdersStayManager.updateByPrimaryKeySelective(purchaseOrdersStayInfo);
			}
			mri.setStayDeliveredQty(mriList.get(0).getStayDeliveredQty()+purchaseOrdersInfo.getStayPurQty());
			materialRepertoryManager.updateByPrimaryKeySelective(mri);
		}
	}

	@Transactional
	public void saveHandworkOrders(PurchaseOrdersInfo purchaseOrdersInfo,Integer purchaseNumber) throws ParseException {
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		//判断是否有明细，且算出总价
		if(purchaseOrdersInfo.getMapList().size()!=0){
			//Map<String,String> mapList = purchaseOrdersInfo.getMapList().get(0);
			PurchaseOrdersEntity poe = new PurchaseOrdersEntity();
//			poe.setPurchaseReason("待采购单下单");
			poe.setPurchaseDate(createDate);
			poe.setPurchasePerson(LoginFilter.getCurrentUser().getUserName());
			poe.setSupplierId(purchaseOrdersInfo.getSupplierId());
			SupplierEntity se = supplierManager.getEntityById(purchaseOrdersInfo.getSupplierId());
			poe.setInboundStatus(0);
			poe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			poe.setFlag(1);
			poe.setPurchasePerson(LoginFilter.getCurrentUser().getUserName());
			poe.setSupplierContact(purchaseOrdersInfo.getSupplierContact());
			poe.setSupplierEmail(purchaseOrdersInfo.getSupplierEmail());
			poe.setSupplierFaxNumber(purchaseOrdersInfo.getSupplierFaxNumber());
			poe.setSupplierPhone(purchaseOrdersInfo.getSupplierPhone());
			poe.setSupplierNumber(purchaseOrdersInfo.getSupplierNumber());
			poe.setPurchasePhoneNumber(purchaseOrdersInfo.getPurchasePhoneNumber());
			poe.setPurchasePhone(purchaseOrdersInfo.getPurchasePhone());
			poe.setPurchaseFaxNumber(purchaseOrdersInfo.getPurchaseFaxNumber());
			poe.setPurchaseEmail(purchaseOrdersInfo.getPurchaseEmail());
			poe.setIfTax(purchaseOrdersInfo.getIfTax());
			poe.setIfFreight(purchaseOrdersInfo.getIfFreight());
			poe.setFreight(purchaseOrdersInfo.getFreight());
			poe.setRemark(purchaseOrdersInfo.getRemark());
			poe.setPurchaseAgreement(purchaseOrdersInfo.getPurchaseAgreement());
			poe.setIfPickUp(purchaseOrdersInfo.getIfPickUp());
			poe.setDeliveryAddressId(purchaseOrdersInfo.getDeliveryAddressId());
			poe.setPurchasePriceTotal(purchaseOrdersInfo.getSubtotal());
			poe.setProductPriceTotal(purchaseOrdersInfo.getProductPriceTotal());
			poe.setRepertoryId(purchaseOrdersInfo.getRepertoryId());
			String pruchaseCode = "Pur" +frontCompWithZore(purchaseNumber,8);
			poe.setPurchaseCode(pruchaseCode);
			poe.setPurchaseNumber(purchaseNumber);
			//判断流程当前状态
			if(workflowIsSuspended()){
				//未启用，记录状态直接通过审核
				poe.setReviewStatus(AUDITING_PASS);
			}else {
				//启用，记录状态待审核
				poe.setReviewStatus(BusinessConstants.STAY_AUDITING);
			}
			PurchaseOrdersEntity purchaseOrdersEntity = purchaseOrdersManager.insertBy(poe);
			//循环明细
			for (int j=0;j<purchaseOrdersInfo.getMapList().size();j++){
				Map<String,String> polilMap = purchaseOrdersInfo.getMapList().get(j);
				PurchaseOrdersListEntity pole = new PurchaseOrdersListEntity();
				pole.setPurchaseOrdersId(purchaseOrdersEntity.getId());
				pole.setPurchasePrice(new BigDecimal(polilMap.get("purchasePrice")));
				pole.setPurchasePriceTotal(new BigDecimal(polilMap.get("purchasePriceTotal")));
				Integer materialId = Integer.parseInt(polilMap.get("materialId"));
				MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
				String numberValue = null;
				numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(),numberValue);
				pole.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
				pole.setNumber(numberValue);
				pole.setMaterialSource(mbe.getSource());
				pole.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
				pole.setOriginalMaterial(mbe.getOriginalMaterial());
				pole.setDrawingCode(mbe.getDrawingCode());
				pole.setPurchaseCode(poe.getPurchaseCode());
				pole.setType(mbe.getType());
				pole.setSupplierName(se.getSupplierName());
				pole.setMaterialId(materialId);
				pole.setMaterialCode(polilMap.get("materialCode"));
				pole.setMaterialName(polilMap.get("materialName"));
				pole.setSupplierId(Integer.parseInt(polilMap.get("supplierId")));
				pole.setPurchaseQty(Float.parseFloat(polilMap.get("purchaseQty")));
				pole.setUnReceivedQty(Float.parseFloat(polilMap.get("purchaseQty")));
				pole.setStayPurQty(0f);
				pole.setRepertoryId(Integer.parseInt(polilMap.get("repertoryId")));
				pole.setReceivedQty(0f);
				pole.setPurchaseReason(polilMap.get("purchaseReason"));
				pole.setPaymentMethod(Integer.parseInt(polilMap.get("query")));
				pole.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				pole.setArriveDate(polilMap.get("purchaseDays"));
				pole.setReceivedStatus(poe.getReviewStatus());
				purchaseOrdersListManager.insertBy(pole);
				//修改仓库里待收货数
				MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
				mre.setMaterialId(Integer.parseInt(polilMap.get("materialId")));
				List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
				if (mreList.size()!=0){
					mre.setId(mreList.get(0).getId());
					mre.setStayDeliveredQty(mreList.get(0).getStayDeliveredQty()+Float.parseFloat(polilMap.get("purchaseQty")));
					if(workflowIsSuspended()) {
						materialRepertoryManager.updateByPrimaryKeySelective(mre);
					}
				}
			}
		}
	}

	@Transactional
	public void saveStayOrders(PurchaseOrdersInfo purchaseOrdersInfo,Integer purchaseNumber) throws ParseException {
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		//判断是否有明细，且算出总价
		if(purchaseOrdersInfo.getMapList().size()!=0){
			Map<String,String> mapList = purchaseOrdersInfo.getMapList().get(0);
			PurchaseOrdersEntity poe = new PurchaseOrdersEntity();
//			poe.setPurchaseReason("待采购单下单");
			poe.setPurchaseDate(createDate);
			poe.setPurchasePerson(LoginFilter.getCurrentUser().getUserName());
			poe.setSupplierId(purchaseOrdersInfo.getSupplierId());
			SupplierEntity se = supplierManager.getEntityById(purchaseOrdersInfo.getSupplierId());
			poe.setInboundStatus(0);
			poe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			poe.setFlag(1);
			poe.setPurchasePerson(LoginFilter.getCurrentUser().getUserName());
			poe.setSupplierContact(purchaseOrdersInfo.getSupplierContact());
			poe.setSupplierEmail(purchaseOrdersInfo.getSupplierEmail());
			poe.setSupplierFaxNumber(purchaseOrdersInfo.getSupplierFaxNumber());
			poe.setSupplierPhone(purchaseOrdersInfo.getSupplierPhone());
			poe.setSupplierNumber(purchaseOrdersInfo.getSupplierNumber());
			poe.setPurchasePhoneNumber(purchaseOrdersInfo.getPurchasePhoneNumber());
			poe.setPurchasePhone(purchaseOrdersInfo.getPurchasePhone());
			poe.setPurchaseFaxNumber(purchaseOrdersInfo.getPurchaseFaxNumber());
			poe.setPurchaseEmail(purchaseOrdersInfo.getPurchaseEmail());
			poe.setIfTax(purchaseOrdersInfo.getIfTax());
			poe.setIfFreight(purchaseOrdersInfo.getIfFreight());
			poe.setFreight(purchaseOrdersInfo.getFreight());
			poe.setRemark(purchaseOrdersInfo.getRemark());
			poe.setPurchaseAgreement(purchaseOrdersInfo.getPurchaseAgreement());
			poe.setIfPickUp(purchaseOrdersInfo.getIfPickUp());
			poe.setDeliveryAddressId(purchaseOrdersInfo.getDeliveryAddressId());
			poe.setPurchasePriceTotal(purchaseOrdersInfo.getSubtotal());
			poe.setProductPriceTotal(purchaseOrdersInfo.getProductPriceTotal());
			poe.setRepertoryId(purchaseOrdersInfo.getRepertoryId());
			String pruchaseCode = "Pur" +frontCompWithZore(purchaseNumber,8);
			poe.setPurchaseCode(pruchaseCode);
			poe.setPurchaseNumber(purchaseNumber);
			//判断流程当前状态
			if(workflowIsSuspended()){
				//未启用，记录状态直接通过审核
				poe.setReviewStatus(AUDITING_PASS);
			}else {
				//启用，记录状态待审核
				poe.setReviewStatus(BusinessConstants.STAY_AUDITING);
			}
			PurchaseOrdersEntity purchaseOrdersEntity = purchaseOrdersManager.insertBy(poe);
			//循环明细
			for (int j=0;j<purchaseOrdersInfo.getMapList().size();j++){
				Map<String,String> polilMap = purchaseOrdersInfo.getMapList().get(j);
				PurchaseOrdersListEntity pole = new PurchaseOrdersListEntity();
				pole.setPurchaseOrdersId(purchaseOrdersEntity.getId());
				pole.setPurchasePrice(new BigDecimal(polilMap.get("purchasePrice")));
				pole.setPurchasePriceTotal(new BigDecimal(polilMap.get("purchasePriceTotal")));
				Integer materialId = Integer.parseInt(polilMap.get("materialId"));
				MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
				String numberValue = null;
				numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(),numberValue);
				pole.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
				pole.setNumber(numberValue);
				pole.setMaterialSource(mbe.getSource());
				pole.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
				pole.setOriginalMaterial(mbe.getOriginalMaterial());
				pole.setDrawingCode(mbe.getDrawingCode());
				pole.setPurchaseCode(poe.getPurchaseCode());
				pole.setType(mbe.getType());
				pole.setSupplierName(se.getSupplierName());
				pole.setMaterialId(materialId);
				pole.setMaterialCode(polilMap.get("materialCode"));
				pole.setMaterialName(polilMap.get("materialName"));
				pole.setSupplierId(Integer.parseInt(polilMap.get("supplierId")));
				pole.setPurchaseQty(Float.parseFloat(polilMap.get("purchaseQty")));
				pole.setUnReceivedQty(Float.parseFloat(polilMap.get("purchaseQty")));
				pole.setStayPurQty(Float.parseFloat(polilMap.get("purchaseQty")));
				pole.setRepertoryId(Integer.parseInt(polilMap.get("repertoryId")));
				pole.setReceivedQty(0f);
				pole.setPurchaseReason(polilMap.get("purchaseReason"));
				pole.setPaymentMethod(Integer.parseInt(polilMap.get("query")));
				pole.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				pole.setArriveDate(polilMap.get("purchaseDays"));
				pole.setReceivedStatus(poe.getReviewStatus());
				pole = purchaseOrdersListManager.insertBy(pole);
				//修改仓库里待收货数
				MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
				mre.setMaterialId(Integer.parseInt(polilMap.get("materialId")));
				List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
				//判断当前带采购单是否已经下单
				PurchaseOrdersStayEntity purchaseOrdersStayEntity = purchaseOrdersStayManager.getEntityById(Integer.parseInt(polilMap.get("purchaseStayId")));
				Float diffQty = 0f;
				if (purchaseOrdersStayEntity.getPurchaseStayStatus() == 0){
					diffQty = Float.parseFloat(polilMap.get("purchaseQty"))-Float.parseFloat(polilMap.get("oldPurchaseQty"));
				}else {
					diffQty = Float.parseFloat(polilMap.get("purchaseQty"));
				}
				PurchaseOrdersStayDetailEntity purchaseOrdersStayDetailEntity = new PurchaseOrdersStayDetailEntity();
				purchaseOrdersStayDetailEntity.setOrderStayId(Integer.parseInt(polilMap.get("purchaseStayId")));
				purchaseOrdersStayDetailEntity.setOrderId(purchaseOrdersEntity.getId());
				purchaseOrdersStayDetailEntity.setOrderListId(pole.getId());
				purchaseOrdersStayDetailEntity.setOrderedNumber(Float.parseFloat(polilMap.get("purchaseQty"))-diffQty);
				purchaseOrdersStayDetailEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				purchaseOrdersStayDetailEntity.setStatus(1);

				purchaseOrdersStayDetailManager.insertBy(purchaseOrdersStayDetailEntity);

				if (mreList.size()!=0){
					mre.setId(mreList.get(0).getId());
					mre.setStayDeliveredQty(mreList.get(0).getStayDeliveredQty()+Float.parseFloat(polilMap.get("purchaseQty")));
					if (mreList.get(0).getStayPurchaseQty()-Float.parseFloat(polilMap.get("purchaseQty"))>0){
						mre.setStayPurchaseQty(mreList.get(0).getStayPurchaseQty()-Float.parseFloat(polilMap.get("purchaseQty")));
					}else {
						mre.setStayPurchaseQty(0f);
					}
					if(workflowIsSuspended()) {
						materialRepertoryManager.updateByPrimaryKeySelective(mre);
					}
				}

				//如果下单下多了，则改变状态为已下单，且重新计算带采购单
				if (diffQty>=0){
					//修改待采购单状态为已下单
					PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
					pose.setId(Integer.parseInt(polilMap.get("purchaseStayId")));
					pose.setStayPurQty(0f);
					//0未下单 1已下单
					pose.setPurchaseStayStatus(ALREADY_ORDER);
					purchaseOrdersStayManager.updateByPrimaryKeySelective(pose);

					//把多的分给下一条,查询未下单的和状态为1的
					PurchaseOrdersStayEntity stayEntity = new PurchaseOrdersStayEntity();
					stayEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
					stayEntity.setStatus(STATUS_DEFAULT);
					stayEntity.setPurchaseStayStatus(NO_ORDER);
					stayEntity.setMaterialId(Integer.parseInt(polilMap.get("materialId")));
					List<PurchaseOrdersStayEntity> stayEntityList = purchaseOrdersStayManager.getListBy(stayEntity);
					if (stayEntityList.size()!=0){
						for (PurchaseOrdersStayEntity ordersStayEntity : stayEntityList){
							diffQty = diffQty - ordersStayEntity.getStayPurQty();
							//插入待采购明细
							purchaseOrdersStayDetailEntity = new PurchaseOrdersStayDetailEntity();
							purchaseOrdersStayDetailEntity.setOrderStayId(ordersStayEntity.getId());
							purchaseOrdersStayDetailEntity.setOrderId(purchaseOrdersEntity.getId());
							purchaseOrdersStayDetailEntity.setOrderListId(pole.getId());
							purchaseOrdersStayDetailEntity.setStatus(1);
							purchaseOrdersStayDetailEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());

							if (diffQty<0){
								if(Math.abs(ordersStayEntity.getStayPurQty())!=Math.abs(diffQty)) {
									//结算采购数量
									purchaseOrdersStayDetailEntity.setOrderedNumber(ordersStayEntity.getStayPurQty() + diffQty);
									purchaseOrdersStayDetailManager.insertBy(purchaseOrdersStayDetailEntity);
								}
								//如果不够，则更新当前数量，不改变状态，结束循环
								ordersStayEntity.setStayPurQty(-diffQty);
								purchaseOrdersStayManager.updateByPrimaryKeySelective(ordersStayEntity);

								break;
							}else{
								//结算采购数量
								purchaseOrdersStayDetailEntity.setOrderedNumber(ordersStayEntity.getStayPurQty());
								purchaseOrdersStayDetailManager.insertBy(purchaseOrdersStayDetailEntity);
								//如果够则继续减下一条，且把当前待采购单变成已下单
								ordersStayEntity.setPurchaseStayStatus(STATUS_DEFAULT);
								purchaseOrdersStayManager.updateByPrimaryKeySelective(ordersStayEntity);
							}
						}
					}
				}else{
					/*
					 * 创建待采购明细
					 */
					PurchaseOrdersStayDetailEntity posde = new PurchaseOrdersStayDetailEntity();
					//待采购记录id
					posde.setOrderStayId(Integer.parseInt(polilMap.get("purchaseStayId")));
					//采购记录id
					posde.setOrderId(purchaseOrdersEntity.getId());
					//采购记录明细id
					posde.setOrderListId(pole.getId());
					//下单数量
					posde.setOrderedNumber(Math.abs(diffQty));
					posde.setStatus(1);
					posde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
					purchaseOrdersStayDetailManager.insertBy(posde);

					//否则改变待采购数量
					PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
					pose.setId(Integer.parseInt(polilMap.get("purchaseStayId")));
					pose.setStayPurQty(-diffQty);
					purchaseOrdersStayManager.updateByPrimaryKeySelective(pose);
				}
			}
		}

	}

	/**
	 * 取订单记录id，在审批通过后更新仓库入库状态记录
	 * @param purchaseOrdersId
	 */
	public void updateMaterialRepertoryByPurchaseOrdersId(Integer purchaseOrdersId){

		PurchaseOrdersListEntity searchpurchaseOrdersList = new PurchaseOrdersListEntity();
		searchpurchaseOrdersList.setPurchaseOrdersId(purchaseOrdersId);
		List<PurchaseOrdersListEntity> currentOrdersList = purchaseOrdersListManager.getListBy(searchpurchaseOrdersList);
		for(PurchaseOrdersListEntity ordersListEntity:currentOrdersList){
			//修改仓库里待收货数
			MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
			mre.setMaterialId(ordersListEntity.getMaterialId());
			List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
			if (mreList.size()!=0){
				mre.setId(mreList.get(0).getId());
				mre.setStayDeliveredQty(mreList.get(0).getStayDeliveredQty()+ordersListEntity.getPurchaseQty());
				if (mreList.get(0).getStayPurchaseQty()-ordersListEntity.getPurchaseQty()>0){
					mre.setStayPurchaseQty(mreList.get(0).getStayPurchaseQty()-ordersListEntity.getPurchaseQty());
				}else {
					mre.setStayPurchaseQty(0f);
				}
				materialRepertoryManager.updateByPrimaryKeySelective(mre);
			}
		}
	}

	/**
	 * 停用、删除流程时所有待评审记录改为评审通过
	 */
	public void suspendedWorkflow(){
		PurchaseOrdersInfo searchPOI = new PurchaseOrdersInfo();
		searchPOI.setReviewStatus(STAY_AUDITING);
		List<PurchaseOrdersInfo> stayReviewList = this.getListBy(searchPOI);
		for(PurchaseOrdersInfo poi : stayReviewList){
			poi.setReviewStatus(AUDITING_PASS);
			this.saveOrUpdate(poi);
		}
	}

	/**
	 * 当前日期加上天数生成一个新日期
	 * @param days
	 * @return
	 * @throws ParseException
	 */
	public static String getArriveDate(Double days) throws ParseException{
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
		String strDate = simpleDateFormat.format(date);
		long time2 = simpleDateFormat.parse(strDate).getTime();

		time2 += 24 * 60 * 60 * 1000 * days;
		Date da = new Date(time2);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(da));
		return dateFormat.format(da);
	}
	/**
	 * 自动补零
	 * @param sourceDate 参数
	 * @param formatLength 长度
	 * @return
	 */
	public static String frontCompWithZore(int sourceDate, int formatLength) {
		/**
		 * 0 指前面补充零
		 * formatLength 字符总长度为 formatLength
		 * d 代表为正数。
		 */
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}
}
