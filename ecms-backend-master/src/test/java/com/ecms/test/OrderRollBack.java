package com.ecms.test;

import com.ecms.EcmsApplication;
import com.ecms.bean.PurchaseOrdersStayDetailInfo;
import com.ecms.service.PurchaseOrdersStayDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcmsApplication.class)
@SpringBootApplication(exclude={
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class OrderRollBack {
    @Resource
    PurchaseOrdersStayDetailService service;
    @Test
    public void test(){
        PurchaseOrdersStayDetailInfo info = new PurchaseOrdersStayDetailInfo();
        info.setOrderStayId(3910);
        System.out.println(service.getListBy(info).size());
        PurchaseOrdersStayDetailInfo info1 =service.getModel(8);
        System.out.println(info1.getId());
    }
}
