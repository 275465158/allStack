package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.common.BusinessConstants;
import com.ecms.dal.entity.PostEntity;
import com.ecms.dal.mapper.PostMapper;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostManager extends BaseManager<PostEntity> {

	@Resource
	private PostMapper postMapper;

    @Override
    protected BasicMapper<PostEntity> getMapper() {
        return postMapper;
    }

    public PostEntity getByName(String name) {
        PostEntity postEntity = new PostEntity();
        postEntity.setName(name);
        return postMapper.selectOne(postEntity);
    }

    public List<Integer> getPostIdsByName(String name) {
        Condition condition = new Condition(PostEntity.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("companyId", LoginFilter.getCurrentUser().getCompanyId());
        criteria.andEqualTo("status", BusinessConstants.STATUS_DEFAULT);
        criteria.andLike("name", "%" + name + "%");
        List<PostEntity> list = getByCondition(condition);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.stream().map(PostEntity::getId).collect(Collectors.toList());
        }
    }

}
