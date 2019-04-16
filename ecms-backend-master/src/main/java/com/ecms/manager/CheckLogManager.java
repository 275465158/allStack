package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.CheckLogEntity;
import com.ecms.dal.mapper.CheckLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CheckLogManager extends BaseManager<CheckLogEntity> {

	@Resource
	private CheckLogMapper checkLogMapper;

    @Override
    protected BasicMapper<CheckLogEntity> getMapper() {
        return checkLogMapper;
    }

}
