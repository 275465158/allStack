package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryEntity;
import com.ecms.dal.mapper.MaterialRepertoryMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 仓库物料manager
 * @author Richie
 * @version 2018/10/25 17:01
 */
@Component
public class MaterialRepertoryManager extends BaseManager<MaterialRepertoryEntity> {

    @Resource
    private MaterialRepertoryMapper materialRepertoryMapper;

    @Override
    protected BasicMapper<MaterialRepertoryEntity> getMapper() {
        return materialRepertoryMapper;
    }

    public Integer getMriId(Integer materialId){
        return materialRepertoryMapper.getMriId(materialId);
    }

    public List<Map<String,Object>> getListSize(Integer materialId){
        return materialRepertoryMapper.getListSize(materialId);
    }
}
