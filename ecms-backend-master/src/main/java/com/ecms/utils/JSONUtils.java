package com.ecms.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON String 反射 javabean
 * 
 * @author alan.wu
 * 
 *
 */
public class JSONUtils {

	private String sourceStr;
	private Class<?> sourceClazz;

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public Class<?> getSourceClazz() {
		return sourceClazz;
	}

	public void setSourceClazz(Class<?> sourceClazz) {
		this.sourceClazz = sourceClazz;
	}

	/**
	 * 
	 * @param jsonstr:JSONArray
	 *            object string
	 * @param sourceClazz
	 *            列表中原子数据类型
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static <T> List<T> instantiateClassforArray(String jsonstr, Class<?> sourceClazz)
			throws IllegalArgumentException {
		if (jsonstr.isEmpty() || jsonstr.equals("") || jsonstr.length() < 1) {
			throw new IllegalArgumentException("设置的字符串格式不正确");
		}
		List<T> entityList = new ArrayList<T>();
		JSONArray jsonList = JSONArray.fromObject(jsonstr);
		Iterator<Object> it = jsonList.iterator();
		while (it.hasNext()) {
			JSONObject ob = (JSONObject) it.next();
			try {
				T entity = (T) sourceClazz.newInstance();
				Class<?> classType = sourceClazz;
				do {
					Field[] field = classType.getDeclaredFields();
					for (int i = 0; i < field.length; i++) {
						Type fieldType = field[i].getGenericType();
						String fieldName = field[i].getName();
						PropertyDescriptor pd = new PropertyDescriptor(fieldName, sourceClazz);
						Method wM = pd.getWriteMethod();// 获得写方法
						if (wM != null) {
							if (ob.has(fieldName) && !ob.getString(fieldName).equals("null")) {
								/**
								 * <h1>必须小心执行类型转换</h1>
								 */
								if (pd.getPropertyType().toString().equals("class java.util.Date")) {
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
									wM.invoke(entity, dateFormat.parse(ob.getString(fieldName)));
								} else if (pd.getPropertyType().toString().equals("class java.lang.Boolean")) {
									wM.invoke(entity, ob.getBoolean(fieldName));
								} else if (pd.getPropertyType().toString().equals("class java.lang.Integer")) {
									wM.invoke(entity, ob.getInt(fieldName));
								} else if (pd.getPropertyType().toString().equals("class java.lang.Double")) {
									wM.invoke(entity, ob.getDouble(fieldName));
								}else if(pd.getPropertyType().toString().equals("class java.lang.Float")) {
									wM.invoke(entity, Float.valueOf(ob.getString(fieldName)));
								}else {
									wM.invoke(entity, ob.get(fieldName));
								}
							}
						}
					}
					classType = classType.getSuperclass();// 向上遍历父类
				} while (null != classType && !classType.equals(sourceClazz));
				entityList.add(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return entityList;
	}

	/**
	 * 
	 * @param jsonstr:JSONArray
	 *            object string
	 * @param sourceClazz
	 *            列表中原子数据类型
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static <T> T instantiateClassforObject(String jsonstr, Class<?> sourceClazz)
			throws IllegalArgumentException {
		if (jsonstr.isEmpty() || jsonstr.equals("") || jsonstr.length() < 1) {
			throw new IllegalArgumentException("设置的字符串格式不正确");
		}
		T entity = null;
		JSONObject ob = JSONObject.fromObject(jsonstr);
		try {
			entity = (T) sourceClazz.newInstance();
			Class<?> classType = sourceClazz;
			do {
				Field[] field = classType.getDeclaredFields();
				for (int i = 0; i < field.length; i++) {
					Type fieldType = field[i].getGenericType();
					String fieldName = field[i].getName();
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, sourceClazz);
					Method wM = pd.getWriteMethod();// 获得写方法
					if (wM != null) {
						if (ob.has(fieldName) && !ob.getString(fieldName).equals("null")) {
							/**
							 * <h1>必须小心执行类型转换</h1>
							 */
							if (pd.getPropertyType().toString().equals("class java.util.Date")) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
								wM.invoke(entity, dateFormat.parse(ob.getString(fieldName)));
							} else if (pd.getPropertyType().toString().equals("class java.lang.Boolean")) {
								wM.invoke(entity, ob.getBoolean(fieldName));
							} else if (pd.getPropertyType().toString().equals("class java.lang.Integer")) {
								wM.invoke(entity, ob.getInt(fieldName));
							} else if (pd.getPropertyType().toString().equals("class java.lang.Double")) {
								wM.invoke(entity, ob.getDouble(fieldName));
							}else if(pd.getPropertyType().toString().equals("class java.lang.Float")) {
								wM.invoke(entity, Float.valueOf(ob.getString(fieldName)));
							}else {
								wM.invoke(entity, ob.get(fieldName));
							}
						}
					}
				}
				classType = classType.getSuperclass();// 向上遍历父类
			} while (null != classType && !classType.equals(sourceClazz));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

}
