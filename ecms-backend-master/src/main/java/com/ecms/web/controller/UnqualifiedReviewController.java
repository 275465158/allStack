package com.ecms.web.controller;

import com.ecms.bean.UnqualifiedReviewInfo;
import com.ecms.dal.entity.UnqualifiedReviewEntity;
import com.ecms.service.BaseService;
import com.ecms.service.UnqualifiedReviewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 9:55
 */
@RestController
@RequestMapping("unqualifiedReview")
public class UnqualifiedReviewController extends BaseController<UnqualifiedReviewEntity,UnqualifiedReviewInfo> {
    @Resource
    private UnqualifiedReviewService unqualifiedReviewService;
    @Override
    protected BaseService<UnqualifiedReviewEntity, UnqualifiedReviewInfo> getBaseService() {
        return unqualifiedReviewService;
    }
}
