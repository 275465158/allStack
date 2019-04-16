package com.ecms.service;

import com.ecms.bean.SellerCustomerAdditional;
import com.ecms.bean.SellerCustomerIndex;
import com.ecms.bean.SellerCustomerInfo;
import com.ecms.dal.entity.*;
import com.ecms.excel.SellerCustomerTemplate;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SellerCustomerContacterManager;
import com.ecms.manager.SellerCustomerManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ecms.utils.ExcelImportUtil.checkObjFieldIsNotNull;

@Service
public class SellerCustomerService extends BaseService<SellerCustomerEntity, SellerCustomerInfo> {

    @Resource
    private SellerCustomerManager sellerCustomerManager;
    @Resource
    private SellerInvoiceService sellerInvoiceService;
    @Resource
    private SellerDeliveryService sellerDeliveryService;
    
    @Resource
    private SellerPayRuleService sellerPayRuleService;
    @Resource
    private SellerPayTypeService sellerPayTypeService;
    @Resource
    private SellerCustomerContacterManager sellerCustomerContacterManager;
    

    @Override
    protected BaseManager<SellerCustomerEntity> getManager() {
        return sellerCustomerManager;
    }

    public List<String> getProvinces() {
        return sellerCustomerManager.getProvinces(LoginFilter.getCurrentUser().getCompanyId());
    }

    public SellerCustomerIndex getIndexById(int id) {
        return sellerCustomerManager.getIndexById(id);
    }

    public SellerCustomerAdditional getAdditionalById(int id) {
        return sellerCustomerManager.getAdditionalById(id);
    }

    @Transactional
    public void save(SellerCustomerIndex index) {
        SellerCustomerInfo info = index.getCustomerInfo();
        List<String> address = info.getAddress();
        if (address != null && address.size() == 3) {
            info.setAddressProvince(address.get(0));
            info.setAddressCity(address.get(1));
            info.setAddressCounty(address.get(2));
        }
        SellerCustomerInfo entity = saveOrUpdate(info);
        sellerCustomerManager.saveContacter(index.getContacterInfos(), entity.getId());
        sellerCustomerManager.saveAddress(index.getAddressInfos(), entity.getId());
    }

    public List<SellerCustomerTemplate> getExportList(SellerCustomerInfo info) {
        List<SellerCustomerTemplate> list = Lists.newArrayList();
		List<SellerCustomerInfo> supplierInfos = getValidListByInfo(info);
		for (SellerCustomerInfo sellerCustomerInfo : supplierInfos) {
            SellerCustomerTemplate template = ExchangeUtils.exchangeObject(sellerCustomerInfo, SellerCustomerTemplate.class);
            template.setTaxIncludeDisplay(sellerCustomerInfo.getTaxInclude() == 1 ? "是" : "否");
            list.add(template);
        }
        return list;
    }
    
    @Override
	protected void setCustomProperties(List<SellerCustomerInfo> list) {
		for (SellerCustomerInfo sellerCustomerInfo : list) {
			setRepertoryInfo(sellerCustomerInfo);
		}
	}
	public void setRepertoryInfo(SellerCustomerInfo sellerCustomerInfo) {
		if (!StringUtils.isEmpty(sellerCustomerInfo.getTaxInclude())) {
			if (sellerCustomerInfo.getTaxInclude()==0) {
				sellerCustomerInfo.setTemplatetaxInclude("否");
			}else if 
			(sellerCustomerInfo.getTaxInclude()==1) {
				sellerCustomerInfo.setTemplatetaxInclude("是");
			}
//			supplierInfo.setPaymentTypename(LocalCache.get(PurchasePaymentTypeEntity.class, supplierInfo.getPaymentType()).getPaymentName());
		}
	}

    public void upload(List<Object> list) {
        Integer companyId = LoginFilter.getCurrentUser().getCompanyId();
//        Map<String, Integer> repertory = new HashMap<>();
//        Collection<RepertoryEntity> repertoryEntities = LocalCache.getList(RepertoryEntity.class);
        int count=0;
        
        Map<String, Integer> taxInclude = new HashMap<>();
        taxInclude.put("是", 1);
        taxInclude.put("否", 0);
        
		Map<String, String> sellerInvoice = new HashMap<>();
		Collection<SellerInvoiceEntity> SellerInvoiceEntityEntities = sellerInvoiceService.getValidEntityList();
		for (SellerInvoiceEntity entity : SellerInvoiceEntityEntities) {
			if (entity.getName() != null) {
				sellerInvoice.put(entity.getName(), entity.getName());
			}
		}
		Map<String, String> sellerDelivery= new HashMap<>();
		Collection<SellerDeliveryEntity> SellerDeliveryEntityEntities = sellerDeliveryService.getValidEntityList();
		for (SellerDeliveryEntity entity : SellerDeliveryEntityEntities) {
            if (entity.getName() != null) {
            	sellerDelivery.put(entity.getName(), entity.getName());
            }
        }
		Map<String, String> SellerPayRule = new HashMap<>();
		Collection<SellerPayRuleEntity> SellerPayRuleEntityEntities = sellerPayRuleService.getValidEntityList();
		for (SellerPayRuleEntity entity : SellerPayRuleEntityEntities) {
            if (entity.getName() != null) {
            	SellerPayRule.put(entity.getName(), entity.getName());
            }
        }
		
		Map<String, String> sellerPayType = new HashMap<>();
		Collection<SellerPayTypeEntity> SellerPayTypeEntityEntities = sellerPayTypeService.getValidEntityList();
		for (SellerPayTypeEntity entity : SellerPayTypeEntityEntities) {
            if (entity.getName() != null) {
            	sellerPayType.put(entity.getName(), entity.getName());
            }
        }
        for (Object obj : list) {
        	count++;
            SellerCustomerTemplate template = (SellerCustomerTemplate)obj;
            SellerCustomerEntity entity = ExchangeUtils.exchangeObject(template, SellerCustomerEntity.class);
            try {//过滤空行
                if (!checkObjFieldIsNotNull(template)){
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(template.getInvoice())) {

				if(!sellerInvoice.containsKey(template.getInvoice())){
					//抛出异常
					throw new CommonBusinessException("第"+count+"行数据中发票类型不存在,请添加");
				}else {
					entity.setInvoice(sellerInvoice.get(template.getInvoice()));
				}

			}
            if (!StringUtils.isEmpty(template.getTaxIncludeDisplay())) {

				if(!taxInclude.containsKey(template.getTaxIncludeDisplay())){
					//抛出异常
					throw new CommonBusinessException("第"+count+"行数据中是否含税填写错误，请检查");
				}else {
					entity.setTaxInclude(taxInclude.get(template.getTaxIncludeDisplay()));
				}

			}
            if (!StringUtils.isEmpty(template.getDelivery())) {

				if(!sellerDelivery.containsKey(template.getDelivery())){
					//抛出异常
					throw new CommonBusinessException("第"+count+"行数据中送货方式不存在,请添加");
				}else {
					entity.setDelivery(sellerDelivery.get(template.getDelivery()));
				}

			}
            if (!StringUtils.isEmpty(template.getPayRule())) {

				if(!SellerPayRule.containsKey(template.getPayRule())){
					//抛出异常
					throw new CommonBusinessException("第"+count+"行数据中付款规则不存在,请添加");
				}else {
					entity.setPayRule(sellerPayType.get(template.getPayRule()));
				}

			}
            if (!StringUtils.isEmpty(template.getPayType())) {

				if(!sellerPayType.containsKey(template.getPayType())){
					//抛出异常
					throw new CommonBusinessException("第"+count+"行数据中发票类型不存在,请添加");
				}else {
					entity.setPayRule(sellerPayType.get(template.getPayType()));
				}

			}
            entity.setCompanyId(companyId);
            SellerCustomerEntity sce = insertDirectly(entity);
            SellerCustomerContacterEntity scce = new SellerCustomerContacterEntity();
            scce.setCustomerId(sce.getId());
            scce.setName(sce.getContacterName());
            scce.setPhone(sce.getContacterPhone());
            sellerCustomerContacterManager.insertBy(scce);
        }
    }
}
