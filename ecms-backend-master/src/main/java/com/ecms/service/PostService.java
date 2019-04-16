package com.ecms.service;

import com.ecms.bean.PostInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.PostEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.EmployeeManager;
import com.ecms.manager.PostManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class PostService extends BaseService<PostEntity, PostInfo> {

	@Resource
	private PostManager postManager;
    @Resource
    private EmployeeManager employeeManager;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseManager<PostEntity> getManager() {
        return postManager;
    }

    @Override
    protected void doAfterUpdate(PostEntity postEntity) {
        redisCache.update(postEntity);
    }

    @PostConstruct
    public void init() {
        List<PostEntity> list = getValidStatusList();
        redisCache.refresh(list);
    }

    @Transactional
    @Override
    public PostEntity updateStatus(int id) {
        PostEntity entity = super.updateStatus(id);
        if (entity.getStatus() == STATUS_DEFAULT) {
            employeeManager.removeInvalidPost(id);
        }
        doAfterUpdate(entity);
        return entity;
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "number_post_";
    }
}
