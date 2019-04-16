package com.ecms.web.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ecms.bean.SupplierMaterialInfo;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecms.bean.SupplierInfo;
import com.ecms.bean.SupplierModal;
import com.ecms.common.Result;
import com.ecms.dal.entity.SupplierEntity;
import com.ecms.excel.SupplierTemplate;
import com.ecms.service.BaseService;
import com.ecms.service.PurchaseInvoiceTypeService;
import com.ecms.service.PurchasePaymentPeriodService;
import com.ecms.service.PurchasePaymentTypeService;
import com.ecms.service.PurchaseShippingMethodService;
import com.ecms.service.RepertoryService;
import com.ecms.service.SupplierContactService;
import com.ecms.service.SupplierMaterialService;
import com.ecms.service.SupplierService;
import com.ecms.utils.ExcelImportUtil;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("supplier")
public class SupplierController extends BaseController<SupplierEntity, SupplierInfo> {

	@Resource
	private SupplierService supplierService;
	@Resource
	private SupplierContactService contactService;
	@Resource
	private PurchaseInvoiceTypeService invoiceTypeService;
	@Resource
	private PurchasePaymentPeriodService paymentPeriodService;
	@Resource
	private PurchasePaymentTypeService paymentTypeService;
	@Resource
	private PurchaseShippingMethodService shippingMethodService;
	@Resource
	private RepertoryService repertoryService;
	@Resource
	private SupplierMaterialService supplierMaterialService;
	
	@Override
	protected BaseService<SupplierEntity, SupplierInfo> getBaseService() {
		return supplierService;
	}
	
	 @Override
	    protected void uploadData(MultipartFile file) throws IOException {
	        List<Object> list = ExcelImportUtil.importExcel(file.getInputStream(), SupplierTemplate.class);
	        supplierService.upload(list);
	    }

	@Override
	    protected List<SupplierTemplate> getListForDownload() {
	        return supplierService.getExportList();
	    }

	    @Override
	    protected List<SupplierTemplate> getTemplateForDownload() {
	        List<SupplierTemplate> list = new ArrayList<>();
	        list.add(new SupplierTemplate());
	        return list;
	    }
	
	@RequestMapping("info")
	public Result<SupplierModal> info(@RequestParam(value="supplierId",required = false, defaultValue="0")Integer supplierId){
		SupplierModal supplierModal = new SupplierModal();
		SupplierInfo info = new SupplierInfo();
		if(supplierId>0) {
			info = supplierService.getModel(supplierId);
		}
		supplierModal.setSupplier(info);
		supplierModal.setSupplierMaterialList(supplierMaterialService.getBySupplierId(supplierId));
		supplierModal.setSupplierContactList(contactService.getBySupplierId(supplierId));
		supplierModal.setInvoiceTypeList(invoiceTypeService.getValidList());
		supplierModal.setPaymentPeriodList(paymentPeriodService.getValidList());
		supplierModal.setPaymentTypeList(paymentTypeService.getValidList());
		supplierModal.setShippingMethodList(shippingMethodService.getValidList());
		supplierModal.setRepertoryList(repertoryService.getValidList());
		return ResultUtils.success(supplierModal);
	}

	@RequestMapping("saveOrUpdateP")
	public Result<SupplierInfo> saveOrUpdateP(SupplierInfo supplierInfo){
		if(supplierInfo.getId()==null){
			SupplierInfo si = new SupplierInfo();
			si.setSupplierName(supplierInfo.getSupplierName());
			si.setStatus(1);
			si.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			List<SupplierInfo> siList = supplierService.getListBy(si);
			if (siList.size()!=0){
				throw new CommonBusinessException("该公司下已有该供应商名称");
			}
		}
		if (supplierInfo.getAddress().size()!=0){
			supplierInfo.setProvince(supplierInfo.getAddress().get(0).toString());
			supplierInfo.setCity(supplierInfo.getAddress().get(1).toString());
			supplierInfo.setCounty(supplierInfo.getAddress().get(2).toString());
		}
		if(supplierInfo.getSupplierCode()==null||supplierInfo.getSupplierCode()=="") {
			supplierInfo.setSupplierCode(supplierService.getNextId());
		}

		return ResultUtils.success(supplierService.saveOrUpdate(supplierInfo));
	}

	@RequestMapping("getInfo")
	public Result<SupplierInfo> getInfo(Integer id){
		SupplierInfo supplierInfo = supplierService.getModel(id);
		List<Object> address = new ArrayList<>();
		address.add(supplierInfo.getProvince());
		address.add(supplierInfo.getCity());
		address.add(supplierInfo.getCounty());
		supplierInfo.setAddress(address);
		return ResultUtils.success(supplierInfo);
	}

	@RequestMapping("supplierList")
	public Result<List<SupplierInfo>> supplierList(Integer materialId){
		SupplierMaterialInfo smi = new SupplierMaterialInfo();
		smi.setMaterialId(materialId);
		smi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<SupplierMaterialInfo> smiList = supplierMaterialService.getListBy(smi);
		List<SupplierInfo> sList = new ArrayList<>();
		if (smiList.size()!=0){
			for (int i=0;i<smiList.size();i++){
				sList.add(supplierService.getModel(smiList.get(i).getSupplierId()));
			}
		}
		return ResultUtils.success(sList);
	}

	@RequestMapping("siList")
	public Result<List<SupplierInfo>> siList(SupplierInfo info) {
		info.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		ExchangeUtils.convertBlank2Null(info);
		List<SupplierInfo> siList = getBaseService().getListBy(info);
		return ResultUtils.success(siList);
	}

	@RequestMapping("getList")
	public Result<List<SupplierInfo>> getList() {
		SupplierInfo si = new SupplierInfo();
		si.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<SupplierInfo> siList = getBaseService().getListBy(si);
		return ResultUtils.success(siList);
	}
	
}
