package com.ecms.bean;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 供应商供货清单
 */
public class SupplierMaterialInfo extends BaseCompanyAndStatusInfo {

	// 物料名称（产品名称）
	private String materialName;

	// 原图材料
	private String originalMaterial;
	// 页面展示的参数值
	private String materialBomParamValueStr;
	//物料编码
	private String materialCode;

	//物料类型
	private String type;

	//工厂内部编号
	private String factoryMaterialCode;

	//-----------------------------上面的是需要拼接的字段（别的表中的）
	//供应商id
	private Integer supplierId;
	
	//物料id
	private Integer materialId;

	//合格率
	private String qualifiedPercent;

	//入库仓库
	private Integer repertoryId;
	
	//价格
	private Float price;
	//重量
	private Double weight;
	
	//最小下单数
	private Float minQty;
	
	//采购方式
	private String purchaseMethod;
	
	//到货天数
	private String purchaseDays;
	
	//物料实体
	private MaterialBomInfo materialBom;
	
	//供应商实体
	private SupplierInfo supplier;
	
	//仓库实体
	private RepertoryInfo repertory;

	//结算方式名称
	private String paymentName;

	//发票类型名称
	private String typeName;

	//付款时间名称
	private String periodName;

	//送货方式
	private String methodName;

	private List<SupplierMaterialInfo> materialList;

	private List<Map<String,String>> materialMap;

	//接收需要修改的价格list
	private List<Map<String,String>> priceMap;

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getOriginalMaterial() {
		return originalMaterial;
	}

	public void setOriginalMaterial(String originalMaterial) {
		this.originalMaterial = originalMaterial;
	}

	public String getMaterialBomParamValueStr() {
		return materialBomParamValueStr;
	}

	public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
		this.materialBomParamValueStr = materialBomParamValueStr;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public String getQualifiedPercent() { return qualifiedPercent; }

	public void setQualifiedPercent(String qualifiedPercent) { this.qualifiedPercent = qualifiedPercent; }

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}

	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
	}

	public SupplierInfo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getMinQty() {
		return minQty;
	}

	public void setMinQty(Float minQty) {
		this.minQty = minQty;
	}

	public String getPurchaseMethod() {
		return purchaseMethod;
	}

	public void setPurchaseMethod(String purchaseMethod) {
		this.purchaseMethod = purchaseMethod;
	}

	public String getPurchaseDays() {
		return purchaseDays;
	}

	public void setPurchaseDays(String purchaseDays) {
		this.purchaseDays = purchaseDays;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public RepertoryInfo getRepertory() {
		return repertory;
	}

	public void setRepertory(RepertoryInfo repertory) {
		this.repertory = repertory;
	}

	public String getPaymentName() { return paymentName; }

	public void setPaymentName(String paymentName) { this.paymentName = paymentName; }

	public String getTypeName() { return typeName; }

	public void setTypeName(String typeName) { this.typeName = typeName; }

	public String getPeriodName() { return periodName; }

	public void setPeriodName(String periodName) { this.periodName = periodName; }

	public String getMethodName() { return methodName; }

	public void setMethodName(String methodName) { this.methodName = methodName; }

	public List<SupplierMaterialInfo> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<SupplierMaterialInfo> materialList) {
		this.materialList = materialList;
	}

	public List<Map<String, String>> getMaterialMap() {
		return materialMap;
	}

	public void setMaterialMap(List<Map<String, String>> materialMap) {
		this.materialMap = materialMap;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFactoryMaterialCode() {
		return factoryMaterialCode;
	}

	public void setFactoryMaterialCode(String factoryMaterialCode) {
		this.factoryMaterialCode = factoryMaterialCode;
	}

	public List<Map<String, String>> getPriceMap() {
		return priceMap;
	}

	public void setPriceMap(List<Map<String, String>> priceMap) {
		this.priceMap = priceMap;
	}
}
