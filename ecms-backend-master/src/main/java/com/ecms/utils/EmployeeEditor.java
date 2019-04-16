package com.ecms.utils;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import com.ecms.bean.EmployeeInfo;
import com.ecms.service.EmployeeService;

public class EmployeeEditor extends PropertyEditorSupport {
	private EmployeeService employeeService;

	public EmployeeEditor(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		List<EmployeeInfo> infos = new ArrayList<EmployeeInfo>();
		if (text != null) {
			if(text.indexOf(',')>-1) {
				
				String[] ids = text.split(",");
				for(int i = 0;i<ids.length;i++) {
					EmployeeInfo info = employeeService.getModel(Integer.valueOf(ids[i]));
					infos.add(info);
				}
				
			}else {
				
				EmployeeInfo info = employeeService.getModel(Integer.valueOf(text));
				infos.add(info);
				
			}
			setValue(infos);
		}
	}
}
