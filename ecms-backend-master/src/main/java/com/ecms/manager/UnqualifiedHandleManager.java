package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.UnqualifiedHandleEntity;
import com.ecms.dal.mapper.UnqualifiedHandleMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 10:15
 */
@Component
public class UnqualifiedHandleManager extends BaseManager<UnqualifiedHandleEntity> {
    @Resource
    private UnqualifiedHandleMapper unqualifiedHandleMapper;

    @Override
    protected BasicMapper<UnqualifiedHandleEntity> getMapper() {
        return unqualifiedHandleMapper;
    }
}
