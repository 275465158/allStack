package com.ecms.utils;

import java.beans.PropertyEditorSupport;
import java.util.List;

import com.ecms.bean.MaterialParameterInfo;

public class MaterialParameterEditor extends PropertyEditorSupport {
	
	public MaterialParameterEditor() {
		super();
	}
	@Override
	public void setAsText(String text) {
		if(text==null) {
			throw new IllegalArgumentException("设置的字符串格式不正确");
		}
		List<MaterialParameterInfo> infos = JSONUtils.instantiateClassforArray(text, MaterialParameterInfo.class);
		this.setValue(infos);
	}
}
