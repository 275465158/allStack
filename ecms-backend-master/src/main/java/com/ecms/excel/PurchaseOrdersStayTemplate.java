package com.ecms.excel;

import com.ecms.annotation.ExcelField;
import com.ecms.annotation.ExcelSheet;
import org.apache.poi.hssf.util.HSSFColor;

import javax.validation.constraints.NotNull;
import java.util.List;

@ExcelSheet(name = "待采购列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
//前面应该是名字，后面是表头颜色
public class PurchaseOrdersStayTemplate extends BaseTemplate {

	// 待采购编号
//	@ExcelField(name = "待采购编号")
//	private String purchaseStayCode;

	// 物料编码
	@ExcelField(name = "物料编码")
	private String materialCode;

	// 物料名称
	@ExcelField(name = "物料名称")
	private String materialName;

	// 物料类型
	@ExcelField(name = "物料类型")
	private String materialType;

	// 物料参数
	@ExcelField(name = "物料参数")
	private String materialBomParamValueStr;

	// 材料
	@ExcelField(name = "原图材料")
	private String originalMaterial;

	// 图号
	@ExcelField(name = "图号")
	private String drawingCode;

	// 待采购数量
	@ExcelField(name = "待采购数量")
	private Float stayPurQty;

	// 采购原因
	@ExcelField(name = "采购原因")
	private String purchaseReason;

	// 供应商名称
	@ExcelField(name = "供应商名称")
	private String supplierName;

	// 最晚采购日期
	@ExcelField(name = "最晚采购日期")
	private String latestPurDate;

	// 物料id
//	@ExcelField(name = "物料id")
//	private Integer materialId;


	public Float getStayPurQty() {
		return stayPurQty;
	}

	public void setStayPurQty(Float stayPurQty) {
		this.stayPurQty = stayPurQty;
	}

	public String getPurchaseReason() {
		return purchaseReason;
	}

	public void setPurchaseReason(String purchaseReason) {
		this.purchaseReason = purchaseReason;
	}

	public String getLatestPurDate() {
		return latestPurDate;
	}

	public void setLatestPurDate(String latestPurDate) {
		this.latestPurDate = latestPurDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterialBomParamValueStr() {
		return materialBomParamValueStr;
	}

	public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
		this.materialBomParamValueStr = materialBomParamValueStr;
	}

	public String getOriginalMaterial() {
		return originalMaterial;
	}

	public void setOriginalMaterial(String originalMaterial) {
		this.originalMaterial = originalMaterial;
	}

	public String getDrawingCode() {
		return drawingCode;
	}

	public void setDrawingCode(String drawingCode) {
		this.drawingCode = drawingCode;
	}
}
