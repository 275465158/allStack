package com.ecms.utils;

import java.beans.PropertyEditorSupport;

/**
 * 
 * @author alan.wu
 * 2018年8月16日
 * @Description 集合类型返回字段数据绑定处理方法
 */
public class CollectionEditorSupport extends PropertyEditorSupport {
	private Class<?> sourceClazz;

	public Class<?> getSourceClazz() {
		return sourceClazz;
	}

	public void setSourceClazz(Class<?> sourceClazz) {
		this.sourceClazz = sourceClazz;
	}
	
	public CollectionEditorSupport(Class<?> sClass) {
		this.setSourceClazz(sClass);
	}

	@Override
	public void setAsText(String text) {
		if(text==null) {
			throw new IllegalArgumentException("设置的字符串格式不正确");
		}
		this.setValue(JSONUtils.instantiateClassforArray(text, sourceClazz));
	}
}
