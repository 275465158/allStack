package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielInfoEntity;
import com.ecms.dal.mapper.MaterielInfoMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/8/28 14:33
 */
@Component
public class MaterielInfoManager extends BaseManager<MaterielInfoEntity> {

    @Resource
    private MaterielInfoMapper materielInfoMapper;
    @Override
    protected BasicMapper<MaterielInfoEntity> getMapper() {
        return materielInfoMapper;
    }
}
