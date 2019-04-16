package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcedureEquiEntity;
import com.ecms.dal.mapper.ProcedureEquiMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProcedureEquiManager extends BaseManager<ProcedureEquiEntity> {
    @Resource
    private ProcedureEquiMapper procedureEquiMapper;

    @Override
    protected BasicMapper<ProcedureEquiEntity> getMapper() {
        return procedureEquiMapper;
    }
    
    public Integer getProcedureNum() {
		return procedureEquiMapper.getProcedureNum();
	}
}