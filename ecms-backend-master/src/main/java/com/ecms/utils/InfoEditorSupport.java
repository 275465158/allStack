package com.ecms.utils;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
/**
 * 
 * @author alan.wu
 * 2018年8月16日
 * @Description 实例数据转换方法
 */

public class InfoEditorSupport extends PropertyEditorSupport {
	private Class<?> sourceClazz;
	
	public Class<?> getSourceClazz() {
		return sourceClazz;
	}
	public void setSourceClazz(Class<?> sourceClazz) {
		this.sourceClazz = sourceClazz;
	}
	
	public InfoEditorSupport(Class<?> sClass) {
		this.setSourceClazz(sClass);
	}
	
	@Override
	public void setAsText(String text) {
		if(text==null) {
			throw new IllegalArgumentException("设置的字符串格式不正确");
		}
		this.setValue(JSONUtils.instantiateClassforObject(text, sourceClazz));
	}
}
