package com.ecms.service;

import com.ecms.bean.UnqualifiedReviewInfo;
import com.ecms.dal.entity.UnqualifiedReviewEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.UnqualifiedReviewManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 9:53
 */
@Service
public class UnqualifiedReviewService extends BaseService<UnqualifiedReviewEntity,UnqualifiedReviewInfo> {
    @Resource
    private UnqualifiedReviewManager unqualifiedReviewManager;
    @Override
    protected BaseManager<UnqualifiedReviewEntity> getManager() {
        return unqualifiedReviewManager;
    }
}
