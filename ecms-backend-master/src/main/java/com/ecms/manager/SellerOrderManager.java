package com.ecms.manager;

import com.ecms.bean.*;
import com.ecms.common.BasicMapper;
import com.ecms.common.RedisCache;
import com.ecms.common.SellerOrderProgress;
import com.ecms.dal.entity.*;
import com.ecms.dal.mapper.SellerOrderLogMapper;
import com.ecms.dal.mapper.SellerOrderMapper;
import com.ecms.dal.mapper.SellerOrderProductMapper;
import com.ecms.service.MaterialBomService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.MoneyUtils;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SellerOrderManager extends BaseManager<SellerOrderEntity> {

    @Resource
    private SellerOrderMapper sellerOrderMapper;
    @Resource
    private SellerOrderProductMapper sellerOrderProductMapper;
    @Resource
    private SellerOrderLogMapper sellerOrderLogMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private MaterialBomManager materialBomManager;
    @Resource
    private MaterialBomService materialBomService;

    @Override
    protected BasicMapper<SellerOrderEntity> getMapper() {
        return sellerOrderMapper;
    }

    public SellerOrderIndex getIndexById(int id) {
        SellerOrderIndex index = new SellerOrderIndex();
        if (id > 0) {
            SellerOrderEntity entity = getEntityById(id);
            if (entity != null) {
                SellerOrderInfo info = ExchangeUtils.exchangeObject(entity, SellerOrderInfo.class);
                info.setTotalMoneyDisplay(MoneyUtils.changeF2Y(info.getTotalMoney()));
                info.setFreightDisplay(MoneyUtils.changeF2Y(info.getFreight()));
                info.setProgressDisplay(SellerOrderProgress.getNameById(info.getProgress()));
                index.setOrderInfo(info);
                index.setProductInfos(getProductList(id));
                index.setLogInfos(getLogList(id));
            }
        }
        index.setPayTypeInfos(ExchangeUtils.exchangeList(redisCache.getList(SellerPayTypeEntity.class), SellerPayTypeInfo.class));
        index.setPayRuleInfos(ExchangeUtils.exchangeList(redisCache.getList(SellerPayRuleEntity.class), SellerPayRuleInfo.class));
        index.setInvoiceInfos(ExchangeUtils.exchangeList(redisCache.getList(SellerInvoiceEntity.class), SellerInvoiceInfo.class));
        index.setDeliveryInfos(ExchangeUtils.exchangeList(redisCache.getList(SellerDeliveryEntity.class), SellerDeliveryInfo.class));
        return index;
    }

    public void insertLog(SellerOrderInfo info) {
        SellerOrderEntity entity = getEntityById(info.getId());
        String content = "";
        if (!info.getContacterName().equals(entity.getContacterName())) {
            content += assembleContent("联系人", entity.getContacterName(), info.getContacterName());
        }
        if (!info.getDeliveryAddress().equals(entity.getDeliveryAddress())) {
            content += assembleContent("送货地址", entity.getDeliveryAddress(), info.getDeliveryAddress());
        }

        if(!StringUtils.isEmpty(info.getDeliveryDate())){
            if (!info.getDeliveryDate().equals(entity.getDeliveryDate())) {
                content += assembleContent("交货日期", entity.getDeliveryDate(), info.getDeliveryDate());
            }
        }
        if (info.getTaxInclude() != entity.getTaxInclude()) {
            content += assembleContent("是否含税", getTaxIncludeDisplay(entity.getTaxInclude()), getTaxIncludeDisplay(info.getTaxInclude()));
        }
        if(!StringUtils.isEmpty(info.getFreight())){
            if (!info.getFreight().equals(entity.getFreight())) {
                content += assembleContent("运费", entity.getFreight(), info.getFreightDisplay());
            }
        }
        if (!info.getPayType().equals(entity.getPayType())) {
            content += assembleContent("付款方式", entity.getPayType(), info.getPayType());
        }
        if (!info.getPayRule().equals(entity.getPayRule())) {
            content += assembleContent("付款规则", entity.getPayRule(), info.getPayRule());
        }
        if (!info.getInvoice().equals(entity.getInvoice())) {
            content += assembleContent("发票类型", entity.getInvoice(), info.getInvoice());
        }
        if (!info.getDelivery().equals(entity.getDelivery())) {
            content += assembleContent("送货方式", entity.getDelivery(), info.getDelivery());
        }
        if (!content.equals("")) {
            SellerOrderLogEntity log = new SellerOrderLogEntity();
            log.setContent(content);
            log.setOperator(LoginFilter.getCurrentUser().getUserName());
            log.setOperation("修改订单");
            log.setOrderId(info.getId());
            sellerOrderLogMapper.insertSelective(log);
        }
    }

    private String assembleContent(String name, String oldValue, String newValue) {
        return name + "：" + oldValue + " 改为 " + newValue + ";";
    }

    private String assembleContent(String name, Integer oldValue, String newValue) {
        String temp = String.valueOf(MoneyUtils.changeF2Y(oldValue));
        return assembleContent(name, temp, newValue);
    }

    private String assembleContent(String name, Integer oldValue, Integer newValue) {
        return assembleContent(name, String.valueOf(oldValue), String.valueOf(newValue));
    }

    private String assembleContent(String name, Float oldValue, Float newValue) {
        return assembleContent(name, String.valueOf(oldValue), String.valueOf(newValue));
    }

    private String getTaxIncludeDisplay(int taxInclude) {
        return taxInclude == 1 ? "是" : "否";
    }

    public void saveProducts(List<SellerOrderProductInfo> list, int orderId, boolean needActionLog) {
        String operator = LoginFilter.getCurrentUser().getUserName();
        Map<Integer, SellerOrderProductEntity> exists = getExistProducts(orderId);
        for (SellerOrderProductInfo info : list) {
            info.setRetailPrice(MoneyUtils.changeY2F(info.getRetailPriceDisplay()));
            info.setTaxes(MoneyUtils.changeY2F(info.getTaxesDisplay()));
            info.setUntaxedPrice(MoneyUtils.changeY2F(info.getUntaxedPriceDisplay()));
            // 如果id非空，说明之前已经存在数据，需要判断是否有做修改
            if (info.getId() != null) {
                SellerOrderProductEntity old = exists.get(info.getId());
                SellerOrderProductEntity sope = ExchangeUtils.exchangeObject(info, SellerOrderProductEntity.class);
                sope.setStatus(1);
                sellerOrderProductMapper.updateByPrimaryKey(sope);
                if (needActionLog) {
                    insertLog(info, old);
                }
                exists.remove(info.getId());
            } else {
                // 需要新增
                SellerOrderProductEntity entity = ExchangeUtils.exchangeObject(info, SellerOrderProductEntity.class);
                entity.setOrderId(orderId);
                sellerOrderProductMapper.insertSelective(entity);
                if (needActionLog) {
                    // 插入操作记录
                    SellerOrderLogEntity log = new SellerOrderLogEntity();
                    log.setContent("产品编码：" + info.getProductCode());
                    log.setOperator(operator);
                    log.setOperation("新增产品");
                    log.setOrderId(orderId);
                    sellerOrderLogMapper.insertSelective(log);
                }
            }
        }
        // 删除废弃的数据
        Set<Integer> set = exists.keySet();
        if (set != null && set.size() > 0) {
            List<Integer> idList = new ArrayList<>(set.size());
            for (Integer id : set) {
                idList.add(id);
                if (needActionLog) {
                    SellerOrderProductEntity entity = sellerOrderProductMapper.selectByPrimaryKey(id);
                    // 插入操作记录
                    SellerOrderLogEntity log = new SellerOrderLogEntity();
                    log.setContent("产品编码：" + entity.getProductCode());
                    log.setOperator(operator);
                    log.setOperation("删除产品");
                    log.setOrderId(orderId);
                    sellerOrderLogMapper.insertSelective(log);
                }
            }
            SellerOrderProductEntity entity = new SellerOrderProductEntity();
            entity.setStatus(STATUS_INVALID);
            Condition condition = new Condition(SellerOrderProductEntity.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andIn("id", idList);
            sellerOrderProductMapper.updateByConditionSelective(entity, condition);
        }
    }

    private void insertLog(SellerOrderProductInfo info, SellerOrderProductEntity old) {
        String content = "";
        if (!info.getProductNumber().equals(old.getProductNumber())) {
            content += assembleContent("产品数量", old.getProductNumber(), info.getProductNumber());
        }
        if(!StringUtils.isEmpty(info.getDeliveryDate())){
            if (!info.getDeliveryDate().equals(old.getDeliveryDate())) {
                content += assembleContent("交货日期", old.getDeliveryDate(), info.getDeliveryDate());
            }
        }
        if(!StringUtils.isEmpty(info.getRetailPrice())){
            if (!info.getRetailPrice().equals(old.getRetailPrice())) {
                content += assembleContent("零售价", old.getRetailPrice(), info.getRetailPriceDisplay());
            }
        }

        if(!StringUtils.isEmpty(info.getTaxRate())){
            if (!info.getTaxRate().equals(old.getTaxRate())) {
                content += assembleContent("税率", old.getTaxRate(), info.getTaxRate());
            }
        }


        if (info.getTaxInclude() != old.getTaxInclude()) {
            content += assembleContent("是否含税", getTaxIncludeDisplay(old.getTaxInclude()), getTaxIncludeDisplay(info.getTaxInclude()));
        }

        if(!StringUtils.isEmpty(info.getUntaxedPrice())){
            if (!info.getUntaxedPrice().equals(old.getUntaxedPrice())) {
                content += assembleContent("未税价格", old.getUntaxedPrice(), info.getUntaxedPriceDisplay());
            }
        }



        if(!StringUtils.isEmpty(info.getTaxes())){
            if (!info.getTaxes().equals(old.getTaxes())) {
                content += assembleContent("税金", old.getTaxes(), info.getTaxesDisplay());
            }
        }

        if (!content.equals("")) {
            SellerOrderLogEntity log = new SellerOrderLogEntity();
            log.setContent(content);
            log.setOperator(LoginFilter.getCurrentUser().getUserName());
            log.setOperation("修改产品(" + info.getId() + ")");
            log.setOrderId(info.getOrderId());
            sellerOrderLogMapper.insertSelective(log);
        }
    }

    private Map<Integer, SellerOrderProductEntity> getExistProducts(int orderId) {
        Map<Integer, SellerOrderProductEntity> map = new HashMap<>();
        List<SellerOrderProductEntity> list = getProductEntityList(orderId);
        for (SellerOrderProductEntity entity : list) {
            map.put(entity.getId(), entity);
        }
        return map;
    }

    private List<SellerOrderProductEntity> getProductEntityList(int orderId) {
        SellerOrderProductEntity entity = new SellerOrderProductEntity();
        entity.setOrderId(orderId);
        entity.setStatus(STATUS_DEFAULT);
        return sellerOrderProductMapper.select(entity);
    }

    private List<SellerOrderProductInfo> getProductList(int orderId) {
        logger.info("--------getProductList begin");
        List<SellerOrderProductEntity> list = getProductEntityList(orderId);
        if (CollectionUtils.isNotEmpty(list)) {
            List<SellerOrderProductInfo> result = ExchangeUtils.exchangeList(list, SellerOrderProductInfo.class);
            for (SellerOrderProductInfo info : result) {
                Integer materialId = info.getProductId();
                if(materialId!=null){
                    MaterialBomInfo mbi = materialBomService.getModel(materialId);
                    info.setSource(mbi.getSource());
                    info.setFactoryMaterialCode(mbi.getFactoryMaterialCode());
                    info.setType(mbi.getType());
                    info.setOriginalMaterial(mbi.getOriginalMaterial());
                    info.setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
                }
                info.setRetailPriceDisplay(MoneyUtils.changeF2Y(info.getRetailPrice()));
                info.setTaxesDisplay(MoneyUtils.changeF2Y(info.getTaxes()));
                info.setUntaxedPriceDisplay(MoneyUtils.changeF2Y(info.getUntaxedPrice()));
            }
            logger.info("--------getProductList end");
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    private List<SellerOrderLogInfo> getLogList(int orderId) {
        logger.info("----getLogList begin");
        List<SellerOrderLogInfo> result = new ArrayList<>();
        SellerOrderLogEntity entity = new SellerOrderLogEntity();
        entity.setOrderId(orderId);
        List<SellerOrderLogEntity> list = sellerOrderLogMapper.select(entity);
        for (SellerOrderLogEntity log : list) {
            SellerOrderLogInfo info = ExchangeUtils.exchangeObject(log, SellerOrderLogInfo.class);
            info.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.getCreated()));
            result.add(info);
        }
        logger.info("----getLogList end");
        return result;
    }

    public void updateByContractId(String contractId){
        sellerOrderMapper.updateByContractId(contractId);
    }

    public Integer getCount(Integer companyId){
        return sellerOrderMapper.getCount(companyId);
    }
}
