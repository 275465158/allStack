package com.ecms.service;

import com.ecms.bean.IncomingUnqualifiedInfo;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

@Service
public class IncomingUnqualifiedService extends BaseService<IncomingUnqualifiedEntity, IncomingUnqualifiedInfo> {

	@Resource
	private IncomingUnqualifiedManager incomingUnqualifiedManager;

	@Resource
	private MaterialRepertoryManager materialRepertoryManager;

	@Resource
	private PurchaseOrdersStayManager purchaseOrdersStayManager;

	@Resource
	private ReturnedPurchaseManager returnedPurchaseManager;

	@Resource
	private MaterialRepertoryIncomingCheckManager materialRepertoryIncomingCheckManager;

	@Resource
	private PurchaseOrdersListManager purchaseOrdersListManager;

	@Resource
	private MaterialBomManager materialBomManager;

	@Override
	protected BaseManager<IncomingUnqualifiedEntity> getManager() {
		return incomingUnqualifiedManager;
	}

	public void returnMaterial(IncomingUnqualifiedInfo info){
		Integer checkId = info.getCheckId();
		MaterialRepertoryIncomingCheckEntity mrice = materialRepertoryIncomingCheckManager.getEntityById(checkId);
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		ReturnedPurchaseEntity rpe = new ReturnedPurchaseEntity();
		rpe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		rpe.setSupplierId(mrice.getSupplierId());
		rpe.setMaterialBatch(mrice.getMaterialBatch());
		rpe.setMaterialName(info.getMaterialName());
		rpe.setMaterialCode(info.getMaterialCode());
		rpe.setPurchaseOrdersId(info.getPurchaseOrdersId());
		rpe.setPurchaseCode(info.getPurchaseCode());
		rpe.setReturnedPurchaseQty(info.getUnqualifiedQty());
		rpe.setPurchaseOrdersListId(info.getPurchaseOrdersListId());
		rpe.setReturnedPurchaseReason("来料检验不合格处理");
		rpe.setRepertoryId(mrice.getRepertoryId());
		rpe.setMaterialId(info.getMaterialId());
		rpe.setPerson(LoginFilter.getCurrentUser().getUserName());
		rpe.setOperationDate(createDate);
		ReturnedPurchaseEntity returnedPurchaseEntity = returnedPurchaseManager.insertBy(rpe);
		String returnedCode = "RET"+frontCompWithZore(returnedPurchaseEntity.getId(),8);
		returnedPurchaseEntity.setReturnedCode(returnedCode);
		returnedPurchaseManager.updateByPrimaryKeySelective(returnedPurchaseEntity);
		againPurchase(info.getMaterialId(),info.getUnqualifiedQty(),"退货");
	}

	public void exchange(IncomingUnqualifiedInfo info){
		PurchaseOrdersListEntity pole = new PurchaseOrdersListEntity();
		pole.setMaterialId(info.getMaterialId());
		pole.setPurchaseOrdersId(info.getPurchaseOrdersId());
		List<PurchaseOrdersListEntity> poleList = purchaseOrdersListManager.getListBy(pole);
		if (poleList.size()!=0){
			//如果已经核销，则变成待核销
			if (poleList.get(0).getVerificatStatus() == 1){
				pole.setVerificatStatus(0);
				pole.setId(poleList.get(0).getId());
				purchaseOrdersListManager.updateByPrimaryKeySelective(pole);
			}
		}
	}

	public void concessions(IncomingUnqualifiedInfo info){
		String purchaseCode = info.getPurchaseCode();
		Integer materialId = info.getMaterialId();
		MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
		MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
		mrice.setMaterialId(materialId);
		mrice.setPurchaseCode(purchaseCode);
		mrice.setMaterialBatch(info.getMaterialBatch());
		List<MaterialRepertoryIncomingCheckEntity> mriciList = materialRepertoryIncomingCheckManager.getListBy(mrice);
		if (mriciList.size()!=0){
			MaterialRepertoryIncomingCheckEntity mriceOne = mriciList.get(0);
			//如果合格品还是待入库，则把不合格品直接加到合格品上
			if (mriceOne.getInboundStutas()==2){
				Integer id = mriceOne.getId();
				mriceOne.setQualifiedQty(mriceOne.getQualifiedQty()+info.getUnqualifiedQty());
				mriceOne.setUnqualifiedQty(mriceOne.getUnqualifiedQty()-info.getUnqualifiedQty());
				mriceOne.setQualifiedPercent("100%");
				mriceOne.setId(id);
				materialRepertoryIncomingCheckManager.updateByPrimaryKeySelective(mriceOne);
			}else{
				//如果已经入库的则生成一条待入库记录
				MaterialRepertoryIncomingCheckEntity materialRepertoryIncomingCheckEntity = new MaterialRepertoryIncomingCheckEntity();
				materialRepertoryIncomingCheckEntity.setRepertoryId(mriceOne.getRepertoryId());
				materialRepertoryIncomingCheckEntity.setMaterialId(info.getMaterialId());
				materialRepertoryIncomingCheckEntity.setInventoryQty(0f);
				materialRepertoryIncomingCheckEntity.setSupplierId(mriceOne.getSupplierId());
				materialRepertoryIncomingCheckEntity.setMaterialBatch(mriceOne.getMaterialBatch());
				materialRepertoryIncomingCheckEntity.setMaterialCode(info.getMaterialCode());
				materialRepertoryIncomingCheckEntity.setMaterialName(info.getMaterialName());
				materialRepertoryIncomingCheckEntity.setOperatingPersonnelId(mriceOne.getOperatingPersonnelId());
				materialRepertoryIncomingCheckEntity.setPurchaseReason(mriceOne.getPurchaseReason());
				materialRepertoryIncomingCheckEntity.setPurchaseCode(mriceOne.getPurchaseCode());
				materialRepertoryIncomingCheckEntity.setQualifiedPercent("100%");
				materialRepertoryIncomingCheckEntity.setQualifiedQty(info.getUnqualifiedQty());
				materialRepertoryIncomingCheckEntity.setUnqualifiedQty(0f);
				materialRepertoryIncomingCheckEntity.setCheckQty(info.getUnqualifiedQty());
				materialRepertoryIncomingCheckEntity.setCheckPercent("100%");
				materialRepertoryIncomingCheckEntity.setMaterialSource(1);
				materialRepertoryIncomingCheckEntity.setInboundStutas(2);
				materialRepertoryIncomingCheckEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
				materialRepertoryIncomingCheckEntity.setNumber(mbe.getType());
				materialRepertoryIncomingCheckEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
				materialRepertoryIncomingCheckEntity.setDrawingCode(mbe.getDrawingCode());
				materialRepertoryIncomingCheckEntity.setSource(mbe.getSource());
				materialRepertoryIncomingCheckEntity.setCheckDate(mriceOne.getCheckDate());
				materialRepertoryIncomingCheckEntity.setPurchaseOrdersId(mriceOne.getPurchaseOrdersId());
				materialRepertoryIncomingCheckEntity.setCheckNumber(mriceOne.getCheckNumber());
				materialRepertoryIncomingCheckEntity.setDeliveryCode(mriceOne.getDeliveryCode());
				materialRepertoryIncomingCheckManager.insertBy(materialRepertoryIncomingCheckEntity);
			}
		}
	}

	public void internalloss(IncomingUnqualifiedInfo info){
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		Integer checkId = info.getCheckId();
		MaterialRepertoryIncomingCheckEntity mriceOld = materialRepertoryIncomingCheckManager.getEntityById(checkId);
		MaterialBomEntity mbe = materialBomManager.getEntityById(info.getMaterialId());
		//补进入库单
		MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
		mrice.setUnqualifiedFlag(0);
		mrice.setRepertoryId(mriceOld.getRepertoryId());
		mrice.setMaterialId(mbe.getId());
		mrice.setSupplierId(mriceOld.getSupplierId());
		mrice.setMaterialBatch(info.getMaterialBatch());
		mrice.setMaterialCode(mbe.getMaterialCode());
		mrice.setMaterialName(mbe.getMaterialName());
		mrice.setOperatingPersonnelId(mriceOld.getOperatingPersonnelId());
		mrice.setPurchasePrice(mriceOld.getPurchasePrice());
		mrice.setUnqualifiedQty(info.getUnqualifiedQty());
		mrice.setQualifiedPercent("100%");
		mrice.setPurchaseCode("不合格品内部损耗");
		mrice.setInboundStutas(2);
		mrice.setCheckDate(createDate);
		mrice.setCheckQty(info.getUnqualifiedQty());
		mrice.setCheckPercent("100%");
		mrice.setMaterialSource(1);
		mrice.setQualifiedQty(0f);
		mrice.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
		mrice.setNumber(mbe.getType());
		mrice.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
		mrice.setDrawingCode(mbe.getDrawingCode());
		mrice.setSource(mbe.getSource());
		mrice.setPurchaseReason(info.getPurchaseReason());
		mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		MaterialRepertoryIncomingCheckEntity materialRepertoryIncomingCheckEntity = materialRepertoryIncomingCheckManager.insertBy(mrice);
		materialRepertoryIncomingCheckEntity.setCheckNumber("IQC"+frontCompWithZore(materialRepertoryIncomingCheckEntity.getId(),8));
		materialRepertoryIncomingCheckManager.updateByPrimaryKeySelective(materialRepertoryIncomingCheckEntity);
		againPurchase(info.getMaterialId(),info.getUnqualifiedQty(),"不合格品内部损耗");
	}

	public void changed(IncomingUnqualifiedInfo info){
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
		if(info.getChangedMaterialId()!=null){
			Integer checkId = info.getCheckId();
			MaterialRepertoryIncomingCheckEntity mriceOld = materialRepertoryIncomingCheckManager.getEntityById(checkId);
			MaterialBomEntity mbe = new MaterialBomEntity();
			mbe = materialBomManager.getEntityById(info.getChangedMaterialId());
			//补进入库单
			MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
			mrice.setRepertoryId(mriceOld.getRepertoryId());
			mrice.setMaterialId(mbe.getId());
			mrice.setInventoryQty(info.getUnqualifiedQty());
			mrice.setSupplierId(mriceOld.getSupplierId());
			mrice.setMaterialBatch("物料转换批次");
			mrice.setMaterialCode(mbe.getMaterialCode());
			mrice.setMaterialName(mbe.getMaterialName());
			mrice.setOperatingPersonnelId(mriceOld.getOperatingPersonnelId());
			mrice.setPurchasePrice(mriceOld.getPurchasePrice());
			mrice.setUnqualifiedQty(0f);
			mrice.setQualifiedQty(info.getUnqualifiedQty());
			mrice.setQualifiedPercent("100%");
			mrice.setPurchaseCode("不合格物料转换");
			mrice.setInboundStutas(2);
			mrice.setCheckDate(createDate);
			mrice.setCheckQty(info.getUnqualifiedQty());
			mrice.setCheckPercent("100%");
			mrice.setMaterialSource(1);
			mrice.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
			mrice.setNumber(mbe.getType());
			mrice.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
			mrice.setDrawingCode(mbe.getDrawingCode());
			mrice.setSource(mbe.getSource());
			mrice.setPurchaseReason(info.getPurchaseReason());
			mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			MaterialRepertoryIncomingCheckEntity materialRepertoryIncomingCheckEntity = materialRepertoryIncomingCheckManager.insertBy(mrice);
			materialRepertoryIncomingCheckEntity.setCheckNumber("IQC"+frontCompWithZore(materialRepertoryIncomingCheckEntity.getId(),8));
			materialRepertoryIncomingCheckManager.updateByPrimaryKeySelective(materialRepertoryIncomingCheckEntity);
			againPurchase(info.getMaterialId(),info.getUnqualifiedQty(),"物料转换");
		}
	}

	/****
	 * 计算是否需要重新采购
	 * @param materialId
	 * @param qty
	 */
	public void againPurchase(Integer materialId,Float qty,String reason){
		//获取当前时间，赋值给创建时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createDate = dateFormat.format(date);
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
		mre.setMaterialId(materialId);
		mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
		if (mreList.size()!=0){
			inventoryQty = mreList.get(0).getInventoryQty();
			stayDeliveredQty = mreList.get(0).getStayDeliveredQty();
			stayInboundQty = mreList.get(0).getStayInboundQty();
			orderNeedQty = mreList.get(0).getOrderNeedQty();
			stayPurchaseQty = mreList.get(0).getStayPurchaseQty();
			//算出当前物料可用的数量
			Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty-(qty+orderNeedQty);
			//如果差值小于0  则需要生成待采购记录
			if (differenceQty<0){
				PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
				MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
				pose.setMaterialName(mbe.getMaterialName());
				pose.setMaterialCode(mbe.getMaterialCode());
				pose.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
				pose.setOriginalMaterial(mbe.getOriginalMaterial());
				pose.setMaterialId(materialId);
				pose.setStayPurQty(qty);
				pose.setPurchaseReason(reason+"需重新采购");
				pose.setLatestPurDate(createDate);
				pose.setPurchaseStayStatus(0);
				pose.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				PurchaseOrdersStayEntity PurchaseOrdersStayEntity = purchaseOrdersStayManager.insertBy(pose);
				String purchaseStayCode = "Pur" +frontCompWithZore(PurchaseOrdersStayEntity.getId(),8);
				PurchaseOrdersStayEntity.setPurchaseStayCode(purchaseStayCode);
				purchaseOrdersStayManager.updateByPrimaryKeySelective(PurchaseOrdersStayEntity);
				//修改仓库带采购数
				mre.setStayPurchaseQty(mreList.get(0).getStayPurchaseQty()+qty);
				mre.setId(mreList.get(0).getId());
				materialRepertoryManager.updateByPrimaryKeySelective(mre);
			}
		}
	}

}
