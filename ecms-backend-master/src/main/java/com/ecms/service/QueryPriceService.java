package com.ecms.service;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.dal.entity.OfferPriceEntity;
import com.ecms.dal.entity.ProductListEntity;
import com.ecms.dal.entity.QueryPriceEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.QueryPriceManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class QueryPriceService extends BaseService<QueryPriceEntity, QueryPriceInfo> {

    @Resource
    private QueryPriceManager manager;
    @Resource
    private CompanyService companyService;
    @Resource
    private MaterialBomService materialBomService;
    @Resource
    private ProductListService productListService;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private OfferPriceService offerPriceService;
    @Resource
    private MaterialBomParamNameService materialBomParamNameService;
    @Resource
    private SellerCustomerService sellerCustomerService;
    @Resource
    private UserService userService;

    @Override
    protected BaseManager<QueryPriceEntity> getManager() {
        return manager;
    }

    public String getNextId() {
        return manager.getNextId();
    }

//    public QueryPriceInfo update(QueryPriceInfo info) {
//        return super.saveOrUpdate(info);
//    }

    @Transactional
    public QueryPriceInfo saveOrUpdate(QueryPriceInfo info) {
        info.setSupplierId(LoginFilter.getCurrentUser().getParentId());
//        info.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        UserInfo userInfo=userService.searchById(LoginFilter.getCurrentUser().getId(),LoginFilter.getCurrentUser().getCompanyId());
        info.setCustomerId(userInfo.getCustomerId());
        if (info.isPickUpByCustomer()) {
            info.setAddressId(null);
        }
        info.setQueryPriceFlag(STATUS_DEFAULT);
        info.setOfferPriceFlag(STATUS_DEFAULT);
        int queryId = super.saveOrUpdate(info).getId();
        OfferPriceInfo toOfferPriceInfo = new OfferPriceInfo();
        toOfferPriceInfo.setQueryPriceId(queryId);
        toOfferPriceInfo.setOfferCode(info.getQueryCode());
        toOfferPriceInfo.setCustomerName(LoginFilter.getCurrentUser().getUserName());
        toOfferPriceInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
        offerPriceService.insert(ExchangeUtils.exchangeObject(toOfferPriceInfo, OfferPriceEntity.class));
        for (int i = 0; i < info.getProductList().size(); i++) {
            try {
                String productId = (String) info.getProductList().get(i).get("id");
                ProductListInfo tempinfo = (ProductListInfo) mapToObject(info.getProductList().get(i), ProductListInfo.class);
                tempinfo.setPurchaseNumber(Integer.valueOf((String) info.getProductList().get(i).get("purchaseNumber")));
                tempinfo.setProductId(Integer.valueOf(productId));
                tempinfo.setQueryId(queryId);
                productListService.insertDirectly(ExchangeUtils.exchangeObject(tempinfo, ProductListEntity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return info;
    }

     /**
       * @Author:pratice
       * @date 2019/1/3
       * @Description: map转对象
       */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    /**
     * @Author:iauhsoaix
     * @date 2018/12/20
     * @Description: /searchMaterialList
     */
    public Pager<MaterialBomInfo> searchMaterialList(int pageNum, int pageSize, MaterialBomInfo materialBomInfo) {

        String productype = materialBomInfo.getType();
        String parameter = materialBomInfo.getMaterialBomParamValueStr();


        if (materialBomInfo.getType().equals("")  && materialBomInfo.getMaterialBomParamValueStr().equals("")) {
            Pager<MaterialBomInfo> pager = materialBomService.getModelListByLike(pageNum, pageSize, materialBomInfo, false);
            for (int a = 0; a < pager.getList().size(); a++) {
                int materialId = pager.getList().get(a).getId();
                pager.getList().get(a).setMaterialBomParamValueStr(getParamValue(materialId));
            }
            for (int i = 0; i < pager.getList().size(); i++) {
                pager.getList().get(i).setType(productTypeService.getTypeSting(pager.getList().get(i).getId(), LoginFilter.getCurrentUser().getParentId()));
            }
            return pager;
        }
        materialBomInfo.setType(null);
        materialBomInfo.setMaterialBomParamValueStr(null);
        Pager<MaterialBomInfo> pager = materialBomService.getModelListByLike(pageNum, pageSize, materialBomInfo, false);
        for (int a = 0; a < pager.getList().size(); a++) {
            int materialId = pager.getList().get(a).getId();
            pager.getList().get(a).setMaterialBomParamValueStr(getParamValue(materialId));
        }
        //剔除类型不一致的
        materialBomInfo.setType(productype);
//		if (materialBomInfo.getType() != "" && (materialBomInfo.getMaterialBomParamValueStr() == "" || materialBomInfo.getMaterialBomParamValueStr() == null)) {
        ProductTypeInfo typeInfo = new ProductTypeInfo();
        typeInfo.setNumber(productype);
        typeInfo.setStatus(STATUS_DEFAULT);
        typeInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
        ExchangeUtils.convertBlank2Null(typeInfo);
        List<ProductTypeInfo> typeLsit = productTypeService.getModelList(pageNum, pageSize, typeInfo).getList();
        if (pager.getList() != null && pager.getList().size() != 0) {
            for (int i = 0; i < pager.getList().size(); i++) {
                if (!productype.equals("")) {
                    if (typeLsit != null && typeLsit.size() != 0) {
                        for (ProductTypeInfo type : typeLsit) {
                            if (!pager.getList().get(i).getNumber().equals(type.getNumber())) {
                                pager.getList().set(i, null);
                            }
                        }
                    }
                }
            }
        }
//		}
        pager.getList().removeAll(Collections.singleton(null));
        pager.setTotal(pager.getList().size());
//剔除参数不一致的
        MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
        materialBomParamNameInfo.setMaterialParamNameValue(parameter);
        materialBomParamNameInfo.setStatus(STATUS_DEFAULT);
        materialBomParamNameInfo.setCompanyId(LoginFilter.getCurrentUser().getParentId());
        List<MaterialBomParamNameInfo> parameterList = materialBomParamNameService.getModelListByLike(pageNum, pageSize, materialBomParamNameInfo, false).getList();
        if (pager.getList() != null && pager.getList().size() != 0) {
            for (int i = 0; i < pager.getList().size(); i++) {

                if (!pager.getList().get(i).getMaterialBomParamValueStr().contains(parameter)) {
                    pager.getList().set(i, null);
                }

            }
        }
        pager.getList().removeAll(Collections.singleton(null));
        pager.setTotal(pager.getList().size());
        for (int i = 0; i < pager.getList().size(); i++) {
            pager.getList().get(i).setType(productTypeService.getTypeSting(pager.getList().get(i).getId(), LoginFilter.getCurrentUser().getCompanyId()));
        }
        return pager;
    }    //pageNum,pageSize,materialBomInfo

    public String getParamValue(Integer id) {
        //根据子id去查部件-参数表
        MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
        materialBomParamNameInfo.setMaterialId(id);
        materialBomParamNameInfo.setStatus(1);
        List<MaterialBomParamNameInfo> mbpniList = materialBomParamNameService.getListBy(materialBomParamNameInfo);
        String materialBomParamNameValueStr = "";
        if (mbpniList.size() != 0) {
            for (int j = 0; j < mbpniList.size(); j++) {
                if (j < mbpniList.size() - 1) {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
                } else {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
                }
            }
        }
        if (materialBomParamNameValueStr != "") {
            return (materialBomParamNameValueStr);
        } else {
            return ("");
        }
    }
}

