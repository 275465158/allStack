package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcessEntity;
import org.apache.ibatis.annotations.Update;

public interface ProcessMapper extends BasicMapper<ProcessEntity> {
    @Update("UPDATE process SET default_process = 0")
    Integer updateDefaultProcess();
}
