package com.ecms.service;

import com.ecms.bean.OfferPriceInfo;
import com.ecms.bean.SellerCustomerInfo;
import com.ecms.dal.entity.OfferPriceEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.OfferPriceManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OfferPriceService extends BaseService<OfferPriceEntity, OfferPriceInfo>{

    @Resource
    private OfferPriceManager offerPriceManager;
    @Resource
    private SellerCustomerService sellerCustomerService;
    @Resource
    private QueryPriceService queryPriceService;

    @Override
    protected BaseManager<OfferPriceEntity> getManager(){
        return offerPriceManager;
    }
    /**
     * @Author:pratice
     * @date 2018/12/27
     * @Description:
     */
    public Map<String,String> getDefaultSelect(Integer currentId) {
        Map<String,String> defaultMap = new HashMap<>();
        SellerCustomerInfo sellerCustomerInfo= sellerCustomerService.getModel(queryPriceService.getModel(this.getModel(currentId).getQueryPriceId()).getCustomerId());
        defaultMap.put("paymentRule",sellerCustomerInfo.getPayRule());
        defaultMap.put("paymentType",sellerCustomerInfo.getPayType());
        defaultMap.put("invoiceType",sellerCustomerInfo.getInvoice());
        return defaultMap;
    }

    /**
     * @Author:pratice
     * @date 2019/1/3
     * @Description: 重写
     */
    @Override
    public OfferPriceEntity insert(OfferPriceEntity entity) {
        return insertDirectly(entity);
    }

//    public OfferPriceInfo saveOrUpdate(OfferPriceInfo info) {
//        OfferPriceEntity entity = ExchangeUtils.exchangeObject(info, OfferPriceEntity.class);
//        if (getManager().getEntityById(entity.getId()) != null) {
//            entity = getManager().updateByPrimaryKeySelective(entity);
//            doAfterUpdate(entity);
//        } else {
//            entity = insertBy(info);
//        }
//        return ExchangeUtils.exchangeObject(entity, OfferPriceInfo.class);
//    }

}
