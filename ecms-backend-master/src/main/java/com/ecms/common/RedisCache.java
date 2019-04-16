package com.ecms.common;

import com.ecms.dal.entity.BaseCompanyAndStatusEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.web.filter.LoginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class RedisCache {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    @Resource
    private RedisTemplate redisTemplate;
    @Value("${env}")
    private String env;

    public <E extends BaseCompanyAndStatusEntity> void refresh(List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Class clazz = list.get(0).getClass();
        String key = getKey(clazz);
        redisTemplate.delete(key);
        for (E e : list) {
            redisTemplate.opsForHash().put(key, e.getId(), e);
        }
    }

    public <E extends BaseCompanyAndStatusEntity> void update(E e) {
        if (e == null) {
            return;
        }
        if (e.getStatus() == null || e.getStatus() == 1) {
            // null的情况是新增下会出现
            e.setStatus(1);
            redisTemplate.opsForHash().put(getKey(e.getClass()), e.getId(), e);
        } else {
            redisTemplate.opsForHash().delete(getKey(e.getClass()), e.getId());
        }
    }

    public <E extends BaseCompanyAndStatusEntity> E get(Class<E> clazz, Integer id) {
        Object obj = redisTemplate.opsForHash().get(getKey(clazz), id);
        if (obj == null) {
            logger.error("redis获取缓存出错, class={}, id={}", clazz.getName(), id);
            throw new CommonBusinessException("数据异常");
        }
        return (E)obj;
    }

    public <E extends BaseCompanyAndStatusEntity> Collection<E> getList(Class<E> clazz) {
        int companyId = LoginFilter.getCurrentUser().getCompanyId();
        List<E> result = new ArrayList<>();
        List<E> list = redisTemplate.opsForHash().values(getKey(clazz));
        for (E e : list) {
            if (e.getCompanyId().intValue() == companyId) {
                result.add(e);
            }
        }
        return result;
    }

    private String getKey(Class clazz) {
        return env + "-" + clazz.getName();
    }

}
