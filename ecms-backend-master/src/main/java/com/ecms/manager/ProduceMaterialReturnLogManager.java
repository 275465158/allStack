package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProduceMaterialReturnLogEntity;
import com.ecms.dal.mapper.ProduceMaterialReturnLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2019/1/9 13:59
 */
@Component
public class ProduceMaterialReturnLogManager extends BaseManager<ProduceMaterialReturnLogEntity> {

    @Resource
    private ProduceMaterialReturnLogMapper produceMaterialReturnLogMapper;

    @Override
    protected BasicMapper<ProduceMaterialReturnLogEntity> getMapper() {
        return produceMaterialReturnLogMapper;
    }

    public Float getReturnedQty(Integer materialId,String productionNumber,Integer companyId){
        return produceMaterialReturnLogMapper.getReturnedQty(materialId,productionNumber,companyId);
    }
}
