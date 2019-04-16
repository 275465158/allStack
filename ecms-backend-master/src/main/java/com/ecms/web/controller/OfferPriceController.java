package com.ecms.web.controller;

import com.ecms.bean.OfferPriceInfo;
import com.ecms.bean.ProductListInfo;
import com.ecms.bean.QueryPriceInfo;
import com.ecms.bean.SellerCustomerInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.OfferPriceEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/offerPrice")
public class OfferPriceController extends BaseController<OfferPriceEntity, OfferPriceInfo> {

    @Resource
    private QueryPriceService service;
    @Resource
    private OfferPriceService offerPriceService;
    @Resource
    private SellerCustomerService sellerCustomerService;
    @Resource
    private DeliveryAddressService deliveryAddressService;
    @Resource
    private CompanyService companyService;
    @Resource
    private ProductListService productListService;
    @Override
    protected BaseService<OfferPriceEntity, OfferPriceInfo> getBaseService() {
        return offerPriceService;
    }

    @RequestMapping("list")
    public Result<Pager<OfferPriceInfo>> list(int pageNum, OfferPriceInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<OfferPriceInfo> pager = offerPriceService.getModelList(pageNum, DEFAULT_PAGE_SIZE, info);

        for (int i = 0; i < pager.getList().size(); i++) {
            QueryPriceInfo queryPriceInfo = service.getModel(pager.getList().get(i).getQueryPriceId());
            pager.getList().get(i).setQueryCode(queryPriceInfo.getQueryCode());
//            pager.getList().get(i).setCustomerName(sellerCustomerService.getModel(queryPriceInfo.getCustomerId()).getCustomerName());
            pager.getList().get(i).setQueryDate(queryPriceInfo.getQueryDate());
            pager.getList().get(i).setCustomerName(sellerCustomerService.getModel(queryPriceInfo.getCustomerId()).getCustomerName());
            pager.getList().get(i).setOfferPriceFlag(queryPriceInfo.getOfferPriceFlag());
            if (!queryPriceInfo.getSupplierId().equals(LoginFilter.getCurrentUser().getCompanyId())) {
                pager.getList().set(i, null);
            }
        }
        pager.getList().removeAll(Collections.singleton(null));
        pager.setTotal(pager.getList().size());
        return ResultUtils.success(pager);
    }
    @RequestMapping("/saveOrUpdate")
    public Result<OfferPriceInfo> saveOrUpdate(OfferPriceInfo info) {
        QueryPriceInfo queryPriceInfo=service.getModel(info.getQueryPriceId());
        info.setSupplierName(companyService.getCompany(queryPriceInfo.getSupplierId()).getName());
        if (info.getOfferPriceFlag()==0){
            queryPriceInfo.setOfferPriceFlag(0);
        }else if (info.getOfferPriceFlag()==2){
            queryPriceInfo.setOfferPriceFlag(2);
        }
        service.updateByPrimaryKeySelective(queryPriceInfo);
        if (info.getProductList()!=null){
            for(int i=0;i<info.getProductList().size();i++){
//            ProductListInfo productListInfo=new ProductListInfo();
                try {
                    productListService.saveOrUpdate((ProductListInfo) QueryPriceService.mapToObject(info.getProductList().get(i),ProductListInfo.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ResultUtils.success(getBaseService().saveOrUpdate(info));
    }

    /**
     * @Author:pratice
     * @date 2018/12/27
     * @Description:
     */
    @RequestMapping("getDefaultSelect")
    public Result<Map<String, String>> getDefaultSelect(Integer currentId) {
        return ResultUtils.success(offerPriceService.getDefaultSelect(currentId));
    }

    @RequestMapping("getOfferInfo")
    public Result<OfferPriceInfo> getOfferInfo(Integer id) {
        OfferPriceInfo info = offerPriceService.getModel(id);
        int queryPriceId = info.getQueryPriceId();
        SellerCustomerInfo sellerCustomerInfo= sellerCustomerService.getModel(service.getModel(queryPriceId).getCustomerId());
        info.setCustomerName(sellerCustomerInfo.getCustomerName());
        QueryPriceInfo queryPriceInfo=service.getModel(queryPriceId);
        info.setQueryCode(queryPriceInfo.getQueryCode());
        info.setQueryDate(queryPriceInfo.getQueryDate());
        info.setTaxInclude(queryPriceInfo.getTaxInclude());
        info.setOtherRequire(queryPriceInfo.getOtherRequire());
        info.setOfferDeadline(queryPriceInfo.getOfferDeadline());
        info.setRequireDeliveryDate(queryPriceInfo.getDeliveryDate());
        info.setProductSum(queryPriceInfo.getProductSum());

        if (queryPriceInfo.getAddressId()==null){

        }else {
            String detialAddress = deliveryAddressService.getModel(queryPriceInfo.getAddressId()).getProvince() + deliveryAddressService.getModel(service.getModel(queryPriceId).getAddressId()).getCity() + deliveryAddressService.getModel(service.getModel(queryPriceId).getAddressId()).getCounty();
            info.setDetailAddress(detialAddress);
        }
        return ResultUtils.success(info);
    }
}
