package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProduceScheduleEntity;
import com.ecms.dal.mapper.ProduceScheduleMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProduceScheduleManager extends BaseManager<ProduceScheduleEntity> {

	@Resource
	private ProduceScheduleMapper produceScheduleMapper;

    @Override
    protected BasicMapper<ProduceScheduleEntity> getMapper() {
        return produceScheduleMapper;
    }

}
