package com.ecms.service;

import com.ecms.bean.ProcedureEquiFileInfo;
import com.ecms.dal.entity.ProcedureEquiFileEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProcedureEquiFileManager;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ProcedureEquiFileService extends BaseService<ProcedureEquiFileEntity, ProcedureEquiFileInfo> {
    @Resource
    private ProcedureEquiFileManager procedureEquiFileManager;

    @Override
    protected BaseManager<ProcedureEquiFileEntity> getManager() {
        return procedureEquiFileManager;
    }
    public String getNextFileId(){
		if ((procedureEquiFileManager.getNextFileId())<10) {
			return "PR000"+((procedureEquiFileManager.getNextFileId()+1));
		}
		if ((procedureEquiFileManager.getNextFileId())<100){
			return "PR00"+(procedureEquiFileManager.getNextFileId()+1);
		}
		if ((procedureEquiFileManager.getNextFileId())<1000){
			return "PR0"+(procedureEquiFileManager.getNextFileId()+1);
		}
		if ((procedureEquiFileManager.getNextFileId())<10000){
			return "PR"+(procedureEquiFileManager.getNextFileId()+1);
		}
		return null;
    	
        
    }
}