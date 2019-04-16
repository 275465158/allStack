package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ReturnedPurchaseEntity;
import com.ecms.dal.mapper.ReturnedPurchaseMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/25 14:06
 */
@Component
public class ReturnedPurchaseManager extends BaseManager<ReturnedPurchaseEntity> {
    @Resource
    private ReturnedPurchaseMapper returnedPurchaseMapper;
    @Override
    protected BasicMapper<ReturnedPurchaseEntity> getMapper() {
        return returnedPurchaseMapper;
    }
}
