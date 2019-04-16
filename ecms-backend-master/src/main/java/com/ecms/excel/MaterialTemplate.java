package com.ecms.excel;

import com.ecms.annotation.ExcelField;
import com.ecms.annotation.ExcelSheet;
import org.apache.poi.hssf.util.HSSFColor;

@ExcelSheet(name = "物料列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
//前面应该是名字，后面是表头颜色
public class MaterialTemplate extends BaseTemplate {

	//层级
	@ExcelField(name = "层级")
	private String level;

	//工厂内部编码
	@ExcelField(name = "工厂内部编码")
	private String factoryMaterialCode;

	//物料类型
	@ExcelField(name = "物料类型")
	private String type;

	// 物料名称
	@ExcelField(name = "物料名称")
	private String materialName;

	// 原图材料
	@ExcelField(name = "原图材料")
	private String originalMaterial;

	//参数
	@ExcelField(name = "参数")
	private String materialBomParamValueStr;

	//图号
	@ExcelField(name = "图号")
	private String drawingCode;

	//验收标准
	@ExcelField(name = "验收标准")
	private String acceptanceStandard;

	// 单位
	@ExcelField(name = "单位")
	private String materialUnit;

	//数量
	@ExcelField(name = "数量")
	private Float quantity;

	// 来源
	@ExcelField(name = "来源")
	private String source;

	//来料检
	@ExcelField(name = "来料检")
	private Integer incomingFlag;

	//工艺格式
	@ExcelField(name = "工艺名称")
	private String processFormat;

	//制程号
	@ExcelField(name = "制程号")
	private String processNumber;

	//制程名称
	@ExcelField(name = "制程名称")
	private String processName;

	//人数
	@ExcelField(name = "人数")
	private Integer people;

	//额定工时
	@ExcelField(name = "额定工时")
	private Float ratedTime;

	//验收标准
	@ExcelField(name = "作业指导书")
	private String jobGuide;

	//加工单价
	@ExcelField(name = "加工单价")
	private Float price;

	//完成检
	@ExcelField(name = "完成检")
	private Integer checkFlag;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

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

    public String getAcceptanceStandard() {
        return acceptanceStandard;
    }

    public void setAcceptanceStandard(String acceptanceStandard) {
        this.acceptanceStandard = acceptanceStandard;
    }

    public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getIncomingFlag() {
		return incomingFlag;
	}

	public void setIncomingFlag(Integer incomingFlag) {
		this.incomingFlag = incomingFlag;
	}

	public String getProcessFormat() {
		return processFormat;
	}

	public void setProcessFormat(String processFormat) {
		this.processFormat = processFormat;
	}

	public String getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public Float getRatedTime() {
		return ratedTime;
	}

	public void setRatedTime(Float ratedTime) {
		this.ratedTime = ratedTime;
	}

    public String getJobGuide() {
        return jobGuide;
    }

    public void setJobGuide(String jobGuide) {
        this.jobGuide = jobGuide;
    }

    public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}
}
