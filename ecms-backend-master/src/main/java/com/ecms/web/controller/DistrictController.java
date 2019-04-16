package com.ecms.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.CascaderModal;
import com.ecms.bean.DistrictInfo;
import com.ecms.common.LogCapability;
import com.ecms.common.Result;
import com.ecms.service.DistrictService;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("district")
public class DistrictController implements LogCapability{
	protected Logger logger = getLogger();

	@Resource
	private DistrictService service;
	
	@RequestMapping("list")
	public Result<List<CascaderModal>> list(){
		List<CascaderModal> root = new ArrayList<CascaderModal>();
		List<DistrictInfo> provinceList = service.getProvinceList();
		Iterator<DistrictInfo> it = provinceList.iterator();
		while(it.hasNext()) {
			CascaderModal provinceModal = new CascaderModal();
			DistrictInfo province = it.next();
			provinceModal.setLabel(province.getName());
			provinceModal.setValue(province.getId().toString());
			List<DistrictInfo> cityList = service.getCityListByParentId(province.getId());
			Iterator<DistrictInfo> cityIt = cityList.iterator();
			List<CascaderModal> cityModalList = new ArrayList<CascaderModal>();
			while(cityIt.hasNext()) {
				CascaderModal cityModal = new CascaderModal();


				DistrictInfo city = cityIt.next();
				cityModal.setLabel(city.getName());
				cityModal.setValue(city.getId().toString());
				List<DistrictInfo> countyList = service.getCityListByParentId(city.getId());
				Iterator<DistrictInfo> countyIt = countyList.iterator();
				List<CascaderModal> countyModalList = new ArrayList<CascaderModal>();
				while(countyIt.hasNext()) {
					CascaderModal countyModal = new CascaderModal();
					DistrictInfo county = countyIt.next();
					countyModal.setLabel(county.getName());
					countyModal.setValue(county.getId().toString());
					countyModalList.add(countyModal);
				}
				if(!countyModalList.isEmpty())
				cityModal.setChildren(countyModalList);
				cityModalList.add(cityModal);
			}
			if(!cityModalList.isEmpty())
			provinceModal.setChildren(cityModalList);
			root.add(provinceModal);
		}
		return ResultUtils.success(root);
	}
}
