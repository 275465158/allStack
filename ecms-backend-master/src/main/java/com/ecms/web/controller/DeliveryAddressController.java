package com.ecms.web.controller;

import com.ecms.bean.DeliveryAddressInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.DeliveryAddressEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.service.BaseService;
import com.ecms.service.DeliveryAddressService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController extends BaseController<DeliveryAddressEntity, DeliveryAddressInfo> {

	@Resource
	private DeliveryAddressService service;

	@Override
	protected BaseService<DeliveryAddressEntity, DeliveryAddressInfo> getBaseService() {
		return service;
	}
	@RequestMapping("list")
	public Result<Pager<DeliveryAddressInfo>> list(int pageNum, DeliveryAddressInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		if(LoginFilter.getCurrentUser().getCustomerId()!=null){
			info.setCustomerId(LoginFilter.getCurrentUser().getCustomerId());
		}else {
			info.setCustomerId(0);
		}

		Pager<DeliveryAddressInfo> pager = getBaseService().getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
		List<DeliveryAddressInfo> addAddressList=pager.getList();
		for (int i = 0; i < addAddressList.size(); i++) {
			//MD foreach有毒
			DeliveryAddressInfo infoNeedAddress = addAddressList.get(i);
			DeliveryAddressInfo deliveryAddressInfo = service.getModel(infoNeedAddress.getId());
			List<Object> address = new ArrayList<>();
			address.add(deliveryAddressInfo.getProvince());
			address.add(deliveryAddressInfo.getCity());
			address.add(deliveryAddressInfo.getCounty());
			infoNeedAddress.setAddress(address);
		}
		pager.setList(addAddressList);
		return ResultUtils.success(pager);
	}

	@RequestMapping("/saveOrUpdate")
	public Result<DeliveryAddressInfo> saveOrUpdate(DeliveryAddressInfo info) {
		info.setCustomerId(LoginFilter.getCurrentUser().getCustomerId());
		if(info.isChecked()==true){
			List<DeliveryAddressInfo> tempList=service.getValidList();
			for (int i=0;i<tempList.size();i++){
				tempList.get(i).setFlag(0);
				getBaseService().saveOrUpdate(tempList.get(i));
			}
			info.setFlag(1);
		}
		if(info.isChecked()==false){
			info.setFlag(0);
		}
		if (info.getAddress().size()!=0){
			info.setProvince(info.getAddress().get(0).toString());
			info.setCity(info.getAddress().get(1).toString());
			info.setCounty(info.getAddress().get(2).toString());
		}
		info.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		return ResultUtils.success(getBaseService().saveOrUpdate(info));
	}
	@RequestMapping("getInfo")
	public Result<DeliveryAddressInfo> getInfo(Integer id){
		DeliveryAddressInfo deliveryAddressInfo = service.getModel(id);
		List<Object> address = new ArrayList<>();
		address.add(deliveryAddressInfo.getProvince());
		address.add(deliveryAddressInfo.getCity());
		address.add(deliveryAddressInfo.getCounty());
		deliveryAddressInfo.setAddress(address);
		return ResultUtils.success(deliveryAddressInfo);
	}
	@RequestMapping("logicalDelete")
	public Result<Void> logicalDelete(Integer id){
		DeliveryAddressInfo deliveryAddressInfo = service.getModel(id);
		if (deliveryAddressInfo.getFlag()==1){
			throw new CommonBusinessException("请先将其他地址设为默认");
		}else {
			deliveryAddressInfo.setStatus(0);
			getBaseService().saveOrUpdate(deliveryAddressInfo);
			return ResultUtils.success();
		}

	}
	@RequestMapping("setDefault")
	public Result<Void> setDefault(Integer id){
		List<DeliveryAddressInfo> tempList=service.getValidList();
		for (int i=0;i<tempList.size();i++){
		tempList.get(i).setFlag(0);
		getBaseService().saveOrUpdate(tempList.get(i));
		}
		DeliveryAddressInfo deliveryAddressInfo = service.getModel(id);
		deliveryAddressInfo.setFlag(1);
		getBaseService().saveOrUpdate(deliveryAddressInfo);
		return ResultUtils.success();
	}

	/**
	 * 根据公司id查询地址
	 * @return
	 */
	@RequestMapping("searchByCompanyId")
	public Result<List<DeliveryAddressInfo>> searchByCompanyId(){
		DeliveryAddressInfo deliveryAddressInfo = new DeliveryAddressInfo();
		deliveryAddressInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		deliveryAddressInfo.setStatus(STATUS_DEFAULT);
		List<DeliveryAddressInfo> daiList = service.getListBy(deliveryAddressInfo);
		return ResultUtils.success(daiList);
	}

	/**
	 * 保存从采购下单新增的收货地址
	 * @param deliveryAddressInfo
	 * @return
	 */
	@RequestMapping("saveToPurchase")
	public Result<Void> saveToPurchase(DeliveryAddressInfo deliveryAddressInfo){
		deliveryAddressInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		service.insertBy(deliveryAddressInfo);
		return ResultUtils.success();
	}

}
