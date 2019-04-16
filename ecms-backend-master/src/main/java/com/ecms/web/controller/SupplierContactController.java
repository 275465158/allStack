package com.ecms.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ecms.bean.SupplierContactInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.SupplierContactEntity;
import com.ecms.service.BaseService;
import com.ecms.service.SupplierContactService;
import com.ecms.utils.ResultUtils;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("supplier/contact")
public class SupplierContactController extends BaseController<SupplierContactEntity, SupplierContactInfo> {

	@Resource
	private SupplierContactService contactService;

	@Override
	protected BaseService<SupplierContactEntity, SupplierContactInfo> getBaseService() {
		return contactService;
	}

	@RequestMapping("listBySupplierId")
	public Result<List<SupplierContactInfo>> list(Integer supplierId) {
		List<SupplierContactInfo> list = contactService.getBySupplierId(supplierId);
//		List<SupplierContactInfo> list=contactService.getModelList(DEFAULT_PAGE_SIZE, pageSize, info)
		return ResultUtils.success(list);
	}

	/**
	 * @param supplierId
	 * @return
	 * @author pratice
	 * @date 2018年10月30日
	 */

	@RequestMapping("saveContact")
	public Result<Void> saveContact(String jsonStr, Integer supplierId) {
		// 物理删除
		List<SupplierContactInfo> supplierContactlist = JSON.parseArray(jsonStr, SupplierContactInfo.class);
		List<SupplierContactInfo> delsupplierContactlist=contactService.getBySupplierId(supplierId);
		if (delsupplierContactlist!=null){
			for (int i = 0; i < delsupplierContactlist.size(); i++) {
				contactService.deleteBy(delsupplierContactlist.get(i).getId());
			}
		}
		if (supplierContactlist != null) {
			for (int i = 0; i < supplierContactlist.size(); i++) {
				supplierContactlist.get(i).setId(null);
				// supplierMateriallist.get(i).setStatus(com.ecms.common.BusinessConstants.STATUS_DEFAULT);
				supplierContactlist.get(i).setSupplierId(supplierId);
				contactService.saveOrUpdate(supplierContactlist.get(i));
			}
		}
		return ResultUtils.success();
	}

	@RequestMapping("/saveContactList")
	public Result<Void> saveContactList(SupplierContactInfo supplierContactInfo){
		if (supplierContactInfo.getContactTableData()!=null){
			for (int i=0;i<supplierContactInfo.getContactTableData().size();i++){
				Map<String,String> map = supplierContactInfo.getContactTableData().get(i);
				SupplierContactInfo sci = new SupplierContactInfo();
				sci.setSupplierId(Integer.parseInt(map.get("supplierId")));
				sci.setContactName(map.get("contactName"));
				sci.setDepartmentName(map.get("departmentName"));
				sci.setEmail(map.get("email"));
				sci.setFax(map.get("fax"));
				sci.setMobile(map.get("mobile"));
				sci.setTitle(map.get("title"));
				contactService.insertBy(sci);
			}
		}
		return ResultUtils.success();
	}

	/**
	 * 根据供应商id查询供应商下面的联系人信息
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/searchById")
	public Result<List<SupplierContactInfo>> searchById(Integer supplierId){
		SupplierContactInfo sci = new SupplierContactInfo();
		sci.setSupplierId(supplierId);
		sci.setStatus(STATUS_DEFAULT);
		sci.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<SupplierContactInfo> sciList = contactService.getListBy(sci);
		return ResultUtils.success(sciList);
	}
}
