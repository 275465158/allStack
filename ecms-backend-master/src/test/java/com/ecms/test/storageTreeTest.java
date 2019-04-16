package com.ecms.test;

import com.ecms.EcmsApplication;
import com.ecms.manager.StorageManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcmsApplication.class)
@EnableAutoConfiguration
public class storageTreeTest {

	@Resource
	StorageManager manager;

	@Test
	public void test() {
		String rtn = manager.getStorageTree(1);
		System.out.println(rtn);
	}
}
