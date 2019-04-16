package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.UnqualifiedTypeListEntity;
import com.ecms.dal.mapper.UnqualifiedTypeListMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UnqualifiedTypeListManager extends BaseManager<UnqualifiedTypeListEntity> {

	@Resource
	private UnqualifiedTypeListMapper unqualifiedTypeListMapper;

    @Override
    protected BasicMapper<UnqualifiedTypeListEntity> getMapper() {
        return unqualifiedTypeListMapper;
    }

}
