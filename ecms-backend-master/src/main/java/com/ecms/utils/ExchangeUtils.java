package com.ecms.utils;

import com.ecms.dal.entity.AbstractEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ExchangeUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

	/**
	 * 转换List(函数式编程) List<UserEntity> -> List<UserInfo>
	 * 
	 * @param source
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <S, T> List<T> exchangeList(Collection<S> source, Class<T> clazz) {
		return source.stream().map(x -> exchangeObject(x, clazz)).collect(Collectors.toList());
	}

	/**
	 * 生成<T>的实例，并从source中拷贝属性过来
	 * 
	 * @param source
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T exchangeObject(Object source, Class<T> clazz) {
		T target = null;
		try {
			target = clazz.newInstance();
			BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return target;
	}

	/**
	 * 过滤Bean属性，如果是String类型，则把'', ' '等blank的值转换为null，以方便条件查询
	 * 
	 * @param t
	 * @param <T>
	 */
	public static <T> void convertBlank2Null(T t) {
		try {
			Field[] fieldArray = t.getClass().getDeclaredFields();
			for (Field field : fieldArray) {
				String type = field.getGenericType().toString();
				if ("class java.lang.String".equals(type)) {
					field.setAccessible(true);
					Object value = field.get(t);
					if (value != null && StringUtils.isBlank((String) value)) {
						field.set(t, null);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static List<Integer> getIdList(List<? extends AbstractEntity> list) {
		return list.stream().map(AbstractEntity :: getId).collect(Collectors.toList());
	}

}
