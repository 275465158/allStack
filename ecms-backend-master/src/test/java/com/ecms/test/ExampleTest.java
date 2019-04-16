package com.ecms.test;

import com.ecms.EcmsApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcmsApplication.class)
@EnableAutoConfiguration
public class ExampleTest{


	@Test
	public void test() {
	}
}
