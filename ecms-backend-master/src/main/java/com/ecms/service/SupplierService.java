package com.ecms.service;

import com.ecms.bean.SupplierInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.*;
import com.ecms.excel.SupplierTemplate;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SupplierManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.ecms.utils.ExcelImportUtil.checkObjFieldIsNotNull;


@Service
public class SupplierService extends BaseService<SupplierEntity, SupplierInfo> {

	@Resource
	private SupplierManager supplierManager;
	@Resource
	private RedisCache redisCache;

	@Override
	protected BaseManager<SupplierEntity> getManager() {
		return supplierManager;
	}

	@Override
	protected String getRedisNumberKeyPrefix() {
		return "number_supplier_";
	}

	public String getNextId() {		
	return "GYS-LSNS"+frontCompWithZore(this.getNextNumber(),2);
	}
	public static String frontCompWithZore(int sourceDate, int formatLength) {
	    /**
	     * 0 指前面补充零
	     * formatLength 字符总长度为 formatLength
	     * d 代表为正数。
	     */
	    String newString = String.format("%0" + formatLength + "d", sourceDate);
	    return newString;
	}
//setCustomProperties
	@Override
	protected void setCustomProperties(List<SupplierInfo> list) {
		for (SupplierInfo supplierInfo : list) {
			setSupplierInfo(supplierInfo);
		}
	}
	public void setSupplierInfo(SupplierInfo supplierInfo) {
		if (!StringUtils.isEmpty(supplierInfo.getDefaultRepertory())) {
			supplierInfo.setDefaultRepertoryname(redisCache.get(RepertoryEntity.class, supplierInfo.getDefaultRepertory()).getRepertoryName());
		}
		if (!StringUtils.isEmpty(supplierInfo.getPaymentPeriod())) {
						supplierInfo.setPaymentPeriodname(redisCache.get(PurchasePaymentPeriodEntity.class, supplierInfo.getPaymentPeriod()).getPeriodName());
		}
		if (!StringUtils.isEmpty(supplierInfo.getShippingMethod())) {
			supplierInfo.setShippingMethodname(redisCache.get(PurchaseShippingMethodEntity.class, supplierInfo.getShippingMethod()).getMethodName());
		}
		if (!StringUtils.isEmpty(supplierInfo.getInvoiceType())) {
			PurchaseInvoiceTypeEntity invoiceTypeInfo= redisCache.get(PurchaseInvoiceTypeEntity.class, supplierInfo.getInvoiceType());
			supplierInfo.setInvoiceTypename(invoiceTypeInfo.getTypeName());
		}
		if (!StringUtils.isEmpty(supplierInfo.getPaymentType())) {
			supplierInfo.setPaymentTypename(redisCache.get(PurchasePaymentTypeEntity.class, supplierInfo.getPaymentType()).getPaymentName());
		}
		

	}



	 public void upload(List<Object> list) {
	        Map<String, Integer> repertory = new HashMap<>();
	        Collection<RepertoryEntity> repertoryEntities = redisCache.getList(RepertoryEntity.class);

		 Map<String, Integer> purchaseInvoiceType = new HashMap<>();
		 Collection<PurchaseInvoiceTypeEntity> purchaseInvoiceTypeEntities = redisCache.getList(PurchaseInvoiceTypeEntity.class);

		 Map<String, Integer> shippingMethod = new HashMap<>();
		 Collection<PurchaseShippingMethodEntity> shippingMethodEntities = redisCache.getList(PurchaseShippingMethodEntity.class);

		 Map<String, Integer> paymentType = new HashMap<>();
		 Collection<PurchasePaymentTypeEntity> paymentTypeEntities = redisCache.getList(PurchasePaymentTypeEntity.class);

		 Map<String, Integer> paymentPeriod = new HashMap<>();
		 Collection<PurchasePaymentPeriodEntity> paymentPeriodEntities = redisCache.getList(PurchasePaymentPeriodEntity.class);

		 for (PurchaseInvoiceTypeEntity entity : purchaseInvoiceTypeEntities) {
	            if (entity.getTypeName() != null) {
					purchaseInvoiceType.put(entity.getTypeName(), entity.getId());
	            }
	        }

		 for (PurchaseShippingMethodEntity entity : shippingMethodEntities) {
			 if (entity.getMethodName() != null) {
				 shippingMethod.put(entity.getMethodName(), entity.getId());
			 }
		 }
		 for (PurchasePaymentTypeEntity entity : paymentTypeEntities) {
			 if (entity.getPaymentName() != null) {
				 paymentType.put(entity.getPaymentName(), entity.getId());
			 }
		 }
		 for (RepertoryEntity entity : repertoryEntities) {
			 if (entity.getRepertoryName() != null) {
				 repertory.put(entity.getRepertoryName(), entity.getId());
			 }
		 }

		 for (PurchasePaymentPeriodEntity entity : paymentPeriodEntities) {
			 if (entity.getPeriodName() != null) {
				 paymentPeriod.put(entity.getPeriodName(), entity.getId());
			 }
		 }
		 int count=0;
	        for (Object obj : list) {
	        	count++;
	        	SupplierTemplate template = (SupplierTemplate)obj;
	            SupplierInfo info = ExchangeUtils.exchangeObject(template, SupplierInfo.class);
				try {//过滤空行
					if (!checkObjFieldIsNotNull(template)){
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	            
				if (!StringUtils.isEmpty(template.getDefaultRepertoryname())) {
					if(!repertory.containsKey(template.getDefaultRepertoryname())){
						//抛出异常
						throw new CommonBusinessException("第"+count+"行数据中部分默认仓库不存在");
					}else {
						info.setDefaultRepertory(repertory.get(template.getDefaultRepertoryname()));
					}

	            }
				if (!StringUtils.isEmpty(template.getInvoiceTypename())) {
					if(!purchaseInvoiceType.containsKey(template.getInvoiceTypename())){
						//抛出异常
						throw new CommonBusinessException("第"+count+"行数据中部分发票类型不存在");
					}else {
						info.setInvoiceType(purchaseInvoiceType.get(template.getInvoiceTypename()));
					}
							}

				if (!StringUtils.isEmpty(template.getPaymentPeriodname())) {

					if(!paymentPeriod.containsKey(template.getPaymentPeriodname())){
						//抛出异常
						throw new CommonBusinessException("第"+count+"行数据中部分付款时间不存在");
					}else {
						info.setPaymentPeriod(paymentPeriod.get(template.getPaymentPeriodname()));
					}

				}
				if (!StringUtils.isEmpty(template.getPaymentTypename())) {

					if(!paymentType.containsKey(template.getPaymentTypename())){
						//抛出异常
						throw new CommonBusinessException("第"+count+"行数据中部分付款方式不存在");
					}else {
						info.setPaymentType(paymentType.get(template.getPaymentTypename()));
					}

				}
				if (!StringUtils.isEmpty(template.getShippingMethodname())) {
					if(!shippingMethod.containsKey(template.getShippingMethodname())){
						//抛出异常
						throw new CommonBusinessException("第"+count+"行数据中部分送货方式不存在");
					}else {
						info.setShippingMethod(shippingMethod.get(template.getShippingMethodname()));
					}
				}
				if (!StringUtils.isEmpty(template.getSupplierCode())) {
					List<String> codelist= supplierManager.getAllCode();

					if(!codelist.contains(template.getSupplierCode())){

						insertBy(info);
					}else {
						//抛出异常
						throw new CommonBusinessException("第"+count+"行数据中供应商编号重复");
					}

				}else {
					//抛出异常
					throw new CommonBusinessException("第"+count+"行数据中供应商编号为空");
				}

	        }
	    }
	    public List<SupplierTemplate> getExportList(){
	        List<SupplierTemplate> result = new ArrayList<>();
			List<SupplierInfo> supplierInfos = getValidCompanyList();
	        for (SupplierInfo info : supplierInfos) {
	        	SupplierTemplate supplierTemplate = ExchangeUtils.exchangeObject(info, SupplierTemplate.class);
	            result.add(supplierTemplate);
	        }
	        return result;
	    }
	
}
