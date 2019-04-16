package com.ecms.utils;

import java.beans.PropertyEditorSupport;
import com.ecms.bean.MaterialBomInfo;


public class MaterialBomEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) {
		if(text==null) {
			throw new IllegalArgumentException("设置的字符串格式不正确");
		}
		MaterialBomInfo info = JSONUtils.instantiateClassforObject(text, MaterialBomInfo.class);
		this.setValue(info);
	}

}
