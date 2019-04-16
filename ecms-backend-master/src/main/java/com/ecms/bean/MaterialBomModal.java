package com.ecms.bean;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author alan.wu
 * 2018年8月1日
 * @Description 产品明细相关内容实体
 */
public class MaterialBomModal extends MaterialBomInfo {
	
	//物料属性
	private List<MaterialParameterInfo> materialParameters;
	
	//产品部件
	private List<MaterialPartInfo> materialParts;
	
	//制程列表
	private List<MaterialProcessingInfo> materialProcessing;
	
	//物料属性Map结构
	private Map<String, String> materialParameter;
	
	public List<MaterialPartInfo> getMaterialParts() {
		return materialParts;
	}
	public void setMaterialParts(List<MaterialPartInfo> materialParts) {
		this.materialParts = materialParts;
	}
	public List<MaterialParameterInfo> getMaterialParameters() {
		return materialParameters;
	}
	public void setMaterialParameters(List<MaterialParameterInfo> materialParameters) {
		this.materialParameters = materialParameters;
	}
	public List<MaterialProcessingInfo> getMaterialProcessing() {
		return materialProcessing;
	}
	public void setMaterialProcessing(List<MaterialProcessingInfo> materialProcessing) {
		this.materialProcessing = materialProcessing;
	}
	public Map<String, String> getMaterialParameter() {
		return materialParameter;
	}
	public void setMaterialParameter(Map<String, String> materialParameter) {
		this.materialParameter = materialParameter;
	}

}
