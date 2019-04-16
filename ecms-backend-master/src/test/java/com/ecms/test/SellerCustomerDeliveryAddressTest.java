package com.ecms.test;

import com.ecms.EcmsApplication;
import com.ecms.dal.entity.SellerCustomerDeliveryAddressEntity;
import com.ecms.dal.entity.SellerOrderLogEntity;
import com.ecms.dal.mapper.SellerCustomerDeliveryAddressMapper;
import com.ecms.dal.mapper.SellerOrderLogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcmsApplication.class)
@EnableAutoConfiguration
public class SellerCustomerDeliveryAddressTest {

    @Resource
    private SellerCustomerDeliveryAddressMapper addressMapper;
    @Resource
    private SellerOrderLogMapper sellerOrderLogMapper;

    @Test
    public void test() {
        SellerCustomerDeliveryAddressEntity entity = new SellerCustomerDeliveryAddressEntity();
        entity.setCustomerId(10);
        addressMapper.insertSelective(entity);
    }

    @Test
    public void logTest() {
        SellerOrderLogEntity entity = sellerOrderLogMapper.selectByPrimaryKey(6);
    }
}
