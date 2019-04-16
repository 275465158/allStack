package com.ecms.manager;

import com.ecms.bean.*;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerCustomerContacterEntity;
import com.ecms.dal.entity.SellerCustomerDeliveryAddressEntity;
import com.ecms.dal.entity.SellerCustomerEntity;
import com.ecms.dal.mapper.SellerCustomerContacterMapper;
import com.ecms.dal.mapper.SellerCustomerDeliveryAddressMapper;
import com.ecms.dal.mapper.SellerCustomerMapper;
import com.ecms.utils.ExchangeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class SellerCustomerManager extends BaseManager<SellerCustomerEntity> {

    @Resource
    private SellerCustomerMapper customerMapper;
    @Resource
    private SellerCustomerContacterMapper contacterMapper;
    @Resource
    private SellerCustomerDeliveryAddressMapper addressMapper;

    public List<String> getProvinces(int companyId) {
        return customerMapper.selectProvinces(companyId);
    }

    public SellerCustomerIndex getIndexById(int id) {
        SellerCustomerIndex index = new SellerCustomerIndex();
        if (id > 0) {
            SellerCustomerEntity customerEntity = getEntityById(id);
            if (customerEntity != null) {
                index.setCustomerInfo(ExchangeUtils.exchangeObject(customerEntity, SellerCustomerInfo.class));
                index.setContacterInfos(getContacterList(id));
                index.setAddressInfos(getAddressList(id));
            }
        }
        return index;
    }

    public SellerCustomerAdditional getAdditionalById(int id) {
        SellerCustomerAdditional additional = new SellerCustomerAdditional();
        additional.setContacterInfos(getContacterList(id));
        additional.setAddressInfos(getAddressList(id));
        return additional;
    }

    public void saveContacter(List<SellerCustomerContacterInfo> list, int customerId) {
        List<Integer> idList = new ArrayList<>();
        for (SellerCustomerContacterInfo info : list) {
            // 如果id非空，说明之前已经存在数据，不需要做修改
            if (info.getId() != null) {
                idList.add(info.getId());
            } else {
                // 需要新增
                SellerCustomerContacterEntity entity = ExchangeUtils.exchangeObject(info, SellerCustomerContacterEntity.class);
                entity.setCustomerId(customerId);
                contacterMapper.insertSelective(entity);
                idList.add(entity.getId());
            }
        }
        // 删除废弃的数据
        SellerCustomerContacterEntity entity = new SellerCustomerContacterEntity();
        entity.setStatus(STATUS_INVALID);
        Condition condition = new Condition(SellerCustomerContacterEntity.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerId", customerId);
        if (CollectionUtils.isNotEmpty(idList)) {
            criteria.andNotIn("id", idList);
        }
        contacterMapper.updateByConditionSelective(entity, condition);
    }

    public void saveAddress(List<SellerCustomerDeliveryAddressInfo> list, int customerId) {
        List<Integer> idList = new ArrayList<>();
        for (SellerCustomerDeliveryAddressInfo info : list) {
            // 如果id非空，说明之前已经存在数据，不需要做修改
            if (info.getId() != null) {
                idList.add(info.getId());
            } else {
                // 需要新增
                SellerCustomerDeliveryAddressEntity entity = ExchangeUtils.exchangeObject(info, SellerCustomerDeliveryAddressEntity.class);
                entity.setCustomerId(customerId);
                addressMapper.insertSelective(entity);
                idList.add(entity.getId());
            }
        }
        // 删除废弃的数据
        SellerCustomerDeliveryAddressEntity entity = new SellerCustomerDeliveryAddressEntity();
        entity.setStatus(STATUS_INVALID);
        Condition condition = new Condition(SellerCustomerDeliveryAddressEntity.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("customerId", customerId);
        if (CollectionUtils.isNotEmpty(idList)) {
            criteria.andNotIn("id", idList);
        }
        addressMapper.updateByConditionSelective(entity, condition);
    }

    private List<SellerCustomerContacterInfo> getContacterList(int customerId) {
        SellerCustomerContacterEntity entity = new SellerCustomerContacterEntity();
        entity.setCustomerId(customerId);
        entity.setStatus(STATUS_DEFAULT);
        List<SellerCustomerContacterEntity> list = contacterMapper.select(entity);
        if (CollectionUtils.isNotEmpty(list)) {
            return ExchangeUtils.exchangeList(list, SellerCustomerContacterInfo.class);
        } else {
            return new ArrayList<>();
        }
    }

    private List<SellerCustomerDeliveryAddressInfo> getAddressList(int customerId) {
        SellerCustomerDeliveryAddressEntity entity = new SellerCustomerDeliveryAddressEntity();
        entity.setCustomerId(customerId);
        entity.setStatus(STATUS_DEFAULT);
        List<SellerCustomerDeliveryAddressEntity> list = addressMapper.select(entity);
        if (CollectionUtils.isNotEmpty(list)) {
            return ExchangeUtils.exchangeList(list, SellerCustomerDeliveryAddressInfo.class);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    protected BasicMapper<SellerCustomerEntity> getMapper() {
        return customerMapper;
    }
}
