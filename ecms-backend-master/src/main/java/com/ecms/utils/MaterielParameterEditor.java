package com.ecms.utils;

import java.beans.PropertyEditorSupport;

import java.util.List;


import com.ecms.bean.MaterielParameterInfo;

public class MaterielParameterEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null) {
			throw new IllegalArgumentException("设置的字符串格式不正确");
		}
		List<MaterielParameterInfo> info = JSONUtils.instantiateClassforArray(text, MaterielParameterInfo.class);
		this.setValue(info);
	}
}
