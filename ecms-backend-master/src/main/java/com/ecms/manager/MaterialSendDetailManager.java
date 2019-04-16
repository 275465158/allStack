package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialSendDetailEntity;
import com.ecms.dal.mapper.MaterialSendDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/29 13:45
 */
@Component
public class MaterialSendDetailManager extends BaseManager<MaterialSendDetailEntity> {

    @Resource
    private MaterialSendDetailMapper materialSendDetailMapper;

    @Override
    protected BasicMapper<MaterialSendDetailEntity> getMapper() {
        return materialSendDetailMapper;
    }

    public Float getAlreadySendQty(Integer companyId,String contractId,String orderCode,Integer materialId,Integer sellerOrderProductId){
        return materialSendDetailMapper.getAlreadySendQty( companyId, contractId, orderCode, materialId,sellerOrderProductId);
    }
}
