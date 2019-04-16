package com.ecms.manager;

import com.ecms.bean.MaterialRepertoryDeliveryInfo;
import com.ecms.bean.MaterialRepertoryDetailInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryDeliveryEntity;
import com.ecms.dal.entity.MaterialRepertoryDetailEntity;
import com.ecms.dal.mapper.MaterialRepertoryDeliveryMapper;
import com.ecms.dal.mapper.MaterialRepertoryDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 仓库物料明细manager
 * @author Richie
 * @version 2018/10/25 17:25
 */
@Component
public class MaterialRepertoryDeliveryManager extends BaseManager<MaterialRepertoryDeliveryEntity> {

    @Resource
    private MaterialRepertoryDeliveryMapper materialRepertoryDeliveryMapper;

    @Override
    protected BasicMapper<MaterialRepertoryDeliveryEntity> getMapper() {
        return materialRepertoryDeliveryMapper;
    }

}
