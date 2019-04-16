package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.IncomingUnqualifiedEntity;
import com.ecms.dal.mapper.IncomingUnqualifiedMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class IncomingUnqualifiedManager extends BaseManager<IncomingUnqualifiedEntity> {

	@Resource
	private IncomingUnqualifiedMapper incomingUnqulifiedMapper;

    @Override
    protected BasicMapper<IncomingUnqualifiedEntity> getMapper() {
        return incomingUnqulifiedMapper;
    }

}
