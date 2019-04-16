package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.UnqualifiedReviewEntity;
import com.ecms.dal.mapper.UnqualifiedReviewMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 9:52
 */
@Component
public class UnqualifiedReviewManager extends BaseManager<UnqualifiedReviewEntity> {
    @Resource
    private UnqualifiedReviewMapper unqualifiedReviewMapper;
    @Override
    protected BasicMapper<UnqualifiedReviewEntity> getMapper() {
        return unqualifiedReviewMapper;
    }
}
