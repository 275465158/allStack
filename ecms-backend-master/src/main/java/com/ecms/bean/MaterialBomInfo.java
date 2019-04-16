package com.ecms.bean;

import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author alan.wu
 * 2018年8月1日
 * @Description 产品信息基础实体
 */
public class MaterialBomInfo extends BaseCompanyAndStatusInfo {
	//价格
	private Float price;

	//最小下单数
	private Float minQty;

	//采购方式
	private String purchaseMethod;

//	//重量
	private Double weight;

	//到货天数
	private String purchaseDays;

	//----------------------------------------------供应商选择物料页面需要拼接显示的
	//物料编码
	private String materialCode;

	//工厂所需物料编号
	private String factoryMaterialCode;
	
	//产品编码
	private String productCode;
	
	//物料名称（产品名称）
	private String materialName;

	//名称编号
//	private String nameCode;
	
	//计量单位
	private String materialUnit;

	//单位编号
//	private String unitCode;
	
	//创建人
	private String author;
	
	//是否可编辑
	private Boolean editable;
	
	//创建时间
	private String materialBomCreated;
	
	//原图材料
	private String originalMaterial;

//	//材料编码
//	private String originalCode;

	//仓库id(保存物料时需要)
	private Integer repertoryId;

	//是否有验收标准
	private Integer ifCheck;

	//是否有图纸
	private Integer ifDrawing;

	//产品物料表示 0代表物料，1代表产品
	private Integer flag;

	//来源 0:自制 1:外购
	private String source;

	//工艺id
	private Integer processId;

	//工艺名称
	private String processName;

	//页面显示数量字段
	private BigDecimal quantity;

	//页面传过来接收的验收标准id
	private Integer checkId;

	//页面传过来接收的图纸id
	private Integer drawingId;

	//页面传过来的子物料的id和数量
	private List<Map<String,String>> mapList;

	//页面传过来的参数名称id和值
	private List<Map<String,String>> dynamicParameterName;

	//库存数
	private Float inventoryNum;

	//订单需求数量
	private Float requiredByOrders;

	//页面展示的参数名
	private String materialBomParamNameStr;

	//页面展示的参数值
	private String materialBomParamValueStr;

	private List<JSONObject> processInfo ;

	//库存总数
	private Float onhandTotal;

	//总安全库存数
	private Float safeStockTotal;

	//订单需求数
	private Float requiredByOrdersTotal;

	//参数
	private String paramValue;

	//图号
	private String drawingCode;

	//产品类型编号
	private String number;

	//产品类型名称
	private String type;

	//物料供应商list
	private List<SupplierMaterialInfo> smiList;

	private List<Map<String,Object>> materialList;

	private Integer check;

	private List<Integer> materialIds;

	private Float maxPrice;

	//审核状态0:审核通过，1:审核中，2:待审核，3:审核不通过 4:停用
	private Integer auditingStatus;

	//版本号
	private Integer versionNum;

	//审核人
	private Integer auditingPersonId;

	//审核状态0审核通过1启动评审2待审核
	private Integer checkAuditingStatus;

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

	@Override
	public boolean equals(Object obj) {
		  if(this==obj){
	           return true;
	       }
		if (obj!=null&&obj instanceof MaterialBomInfo) {
			MaterialBomInfo bean = (MaterialBomInfo) obj;
			if (((MaterialBomInfo) obj).getId().equals(this.getId()) ) {
                return true;
            }else{
                return false;
            }
        }
        return false;
		
	}

	@Override
	public int hashCode() {
		return 7*id+31;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Float getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Float inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	public Float getRequiredByOrders() {
		return requiredByOrders;
	}

	public void setRequiredByOrders(Float requiredByOrders) {
		this.requiredByOrders = requiredByOrders;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getFactoryMaterialCode() {
		return factoryMaterialCode;
	}

	public void setFactoryMaterialCode(String factoryMaterialCode) {
		this.factoryMaterialCode = factoryMaterialCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String produceCode) {
		this.productCode = produceCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getMaterialBomCreated() {
		return materialBomCreated;
	}

	public void setMaterialBomCreated(String materialBomCreated) {
		this.materialBomCreated = materialBomCreated;
	}

	public String getOriginalMaterial() {
		return originalMaterial;
	}

	public void setOriginalMaterial(String originalMaterial) {
		this.originalMaterial = originalMaterial;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public Integer getIfCheck() {
		return ifCheck;
	}

	public void setIfCheck(Integer ifCheck) {
		this.ifCheck = ifCheck;
	}

	public Integer getIfDrawing() {
		return ifDrawing;
	}

	public void setIfDrawing(Integer ifDrawing) {
		this.ifDrawing = ifDrawing;
	}


	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getSource() { return source; }

	public void setSource(String source) { this.source = source; }

	public Integer getProcessId() { return processId; }

	public void setProcessId(Integer processId) { this.processId = processId; }

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(Integer drawingId) {
		this.drawingId = drawingId;
	}

	public List<Map<String, String>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, String>> mapList) {
		this.mapList = mapList;
	}

	public List<Map<String, String>> getDynamicParameterName() {
		return dynamicParameterName;
	}

	public void setDynamicParameterName(List<Map<String, String>> dynamicParameterName) {
		this.dynamicParameterName = dynamicParameterName;
	}

	public String getMaterialBomParamNameStr() {
		return materialBomParamNameStr;
	}

	public void setMaterialBomParamNameStr(String materialBomParamNameStr) {
		this.materialBomParamNameStr = materialBomParamNameStr;
	}

	public String getMaterialBomParamValueStr() {
		return materialBomParamValueStr;
	}

	public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
		this.materialBomParamValueStr = materialBomParamValueStr;
	}

	public List<JSONObject> getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(List<JSONObject> processInfo) {
		this.processInfo = processInfo;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Float getOnhandTotal() {
		return onhandTotal;
	}

	public void setOnhandTotal(Float onhandTotal) {
		this.onhandTotal = onhandTotal;
	}

	public Float getSafeStockTotal() {
		return safeStockTotal;
	}

	public void setSafeStockTotal(Float safeStockTotal) {
		this.safeStockTotal = safeStockTotal;
	}

	public Float getRequiredByOrdersTotal() {
		return requiredByOrdersTotal;
	}

	public void setRequiredByOrdersTotal(Float requiredByOrdersTotal) {
		this.requiredByOrdersTotal = requiredByOrdersTotal;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public List<Map<String, Object>> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Map<String, Object>> materialList) {
		this.materialList = materialList;
	}

	public String getDrawingCode() {
		return drawingCode;
	}

	public void setDrawingCode(String drawingCode) {
		this.drawingCode = drawingCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SupplierMaterialInfo> getSmiList() {
		return smiList;
	}

	public void setSmiList(List<SupplierMaterialInfo> smiList) {
		this.smiList = smiList;
	}

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}

    public List<Integer> getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds(List<Integer> materialIds) {
        this.materialIds = materialIds;
    }

	public Float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Float maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getAuditingStatus() {
		return auditingStatus;
	}

	public void setAuditingStatus(Integer auditingStatus) {
		this.auditingStatus = auditingStatus;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getAuditingPersonId() {
		return auditingPersonId;
	}

	public void setAuditingPersonId(Integer auditingPersonId) {
		this.auditingPersonId = auditingPersonId;
	}
	//	public String getNameCode() {
//		return nameCode;
//	}
//
//	public void setNameCode(String nameCode) {
//		this.nameCode = nameCode;
//	}
//
//	public String getUnitCode() {
//		return unitCode;
//	}
//
//	public void setUnitCode(String unitCode) {
//		this.unitCode = unitCode;
//	}
//
//	public String getOriginalCode() {
//		return originalCode;
//	}
//
//	public void setOriginalCode(String originalCode) {
//		this.originalCode = originalCode;
//	}

	public Integer getCheckAuditingStatus() {
		return checkAuditingStatus;
	}

	public void setCheckAuditingStatus(Integer checkAuditingStatus) {
		this.checkAuditingStatus = checkAuditingStatus;
	}
}
