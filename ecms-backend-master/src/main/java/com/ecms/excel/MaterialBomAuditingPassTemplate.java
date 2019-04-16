package com.ecms.excel;

import com.ecms.annotation.ExcelField;
import com.ecms.annotation.ExcelSheet;
import org.apache.poi.hssf.util.HSSFColor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ExcelSheet(name = "库存导入列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
//前面应该是名字，后面是表头颜色
public class MaterialBomAuditingPassTemplate extends BaseTemplate {

	// 工厂内部编号
	@ExcelField(name = "工厂内部编号")
	private String factoryMaterialCode;

	// 物料类型
	@NotNull
	@ExcelField(name = "物料类型")
	private String type;

	// 物料名称
	@NotNull
	@ExcelField(name = "物料名称")
	private String materialName;

	// 材质
	@NotNull
	@ExcelField(name = "材质")
	private String originalMaterial;

	// 规格
	@NotNull
	@ExcelField(name = "规格")
	private String materialBomParamValueStr;

	// 图号
	@ExcelField(name = "图号")
	private String drawingCode;

	// 单位
	@ExcelField(name = "单位")
	private String materialUnit;

	// 来源
	@ExcelField(name = "来源")
	private String source;

	// 供应商名称
	@ExcelField(name = "供应商名称")
	@NotNull
	private String supplierName;

	// 仓库ID
	@ExcelField(name = "仓库名称")
	@NotNull
	private String repertoryName;

	// 数量
	@ExcelField(name = "数量")
	private Float inventoryQty;

	//单价
	@ExcelField(name = "单价")
	private BigDecimal price;

	//单重
	@ExcelField(name = "单重")
	private Double weight;

	public String getRepertoryName() {
		return repertoryName;
	}

	public void setRepertoryName(String repertoryName) {
		this.repertoryName = repertoryName;
	}

	public void setInventoryQty(Float inventoryQty) {
		this.inventoryQty = inventoryQty;
	}

	public Float getInventoryQty() {
		return inventoryQty;
	}


	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	;

	public String getFactoryMaterialCode() {
		return factoryMaterialCode;
	}

	public void setFactoryMaterialCode(String factoryMaterialCode) {
		this.factoryMaterialCode = factoryMaterialCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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

	public String getDrawingCode() {
		return drawingCode;
	}

	public void setDrawingCode(String drawingCode) {
		this.drawingCode = drawingCode;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}
	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
