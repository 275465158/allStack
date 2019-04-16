package com.ecms.test;

import com.ecms.EcmsApplication;
import com.ecms.dal.entity.PostEntity;
import com.ecms.manager.PostManager;
import com.ecms.web.filter.LoginFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcmsApplication.class)
@EnableAutoConfiguration
public class PostTest {

	@Resource
	private PostManager postManager;

    @Test
    public void insert() {
        PostEntity postEntity = new PostEntity();
        postEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        postEntity.setName("资深产品");
        postManager.insertBy(postEntity);
        System.out.println(postEntity.getId());
    }
}
