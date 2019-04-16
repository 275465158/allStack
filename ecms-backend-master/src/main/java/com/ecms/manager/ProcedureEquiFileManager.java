package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcedureEquiFileEntity;
import com.ecms.dal.mapper.ProcedureEquiFileMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProcedureEquiFileManager extends BaseManager<ProcedureEquiFileEntity> {
    @Resource
    private ProcedureEquiFileMapper procedureEquiFileMapper;

    @Override
    protected BasicMapper<ProcedureEquiFileEntity> getMapper() {
        return procedureEquiFileMapper;
    }
    public int getNextFileId() {
    	if(procedureEquiFileMapper.getNextFileid()==null) {
    		return 1;
    	}else {
    		return Integer.valueOf(procedureEquiFileMapper.getNextFileid());
		}
		
	}
    
	public Integer findversion(int id) {
		if(procedureEquiFileMapper.findversion(id)==null) {
    		return 0;
    	}else {
    		return Integer.valueOf(procedureEquiFileMapper.findversion(id));
		}
		}
}