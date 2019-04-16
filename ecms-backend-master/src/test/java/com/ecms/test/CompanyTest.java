package com.ecms.test;

import com.ecms.EcmsApplication;
import com.ecms.bean.CompanyInfo;
import com.ecms.service.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcmsApplication.class)
@EnableAutoConfiguration
public class CompanyTest {

	@Resource
	CompanyService companyService;

	@Test
	public void test() {
		// CompanyInfo companyInfo = new CompanyInfo();
		// companyInfo.setLogo("test");
		// companyInfo.setAddress("ddd");
		// companyInfo.setName("jj");
		// companyInfo.setId(1);
		// companyService.save(companyInfo);
		CompanyInfo companyInfo = companyService.getCompany(1);
		System.out.println(companyInfo.getName());
	}
}
