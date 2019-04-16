package com.ecms.web.controller;

import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.Person;
import com.ecms.common.LogCapability;
import com.ecms.common.Result;
import com.ecms.utils.ResultUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class TestController implements LogCapability {

	private Logger logger = getLogger();

	@RequestMapping("/test")
	public String test() {
		logger.info("hello world");
		return "Hello World";
	}

	@RequestMapping("/table")
	public List<Person> getPerson(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		System.out.println(page);
		Random random = new Random();
		List<Person> list = new ArrayList<Person>();
		for (int i = 0; i < 20; i++) {
			Person person = new Person();
			person.setName("名字" + random.nextInt(100));
			person.setAddress("广东省 地址 " + random.nextInt(1000));
			person.setDate("日期" + random.nextInt(10000));
			list.add(person);
		}
		return list;
	}

	@RequestMapping("/tt")
	public Result<Void> tt(MaterialBomModal mList) {
		System.out.println(mList.getMaterialParameter().size());
		return ResultUtils.success();
	}
	
}
