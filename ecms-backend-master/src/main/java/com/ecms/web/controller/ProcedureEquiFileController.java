package com.ecms.web.controller;

import com.ecms.bean.ProcedureEquiFileInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProcedureEquiFileEntity;
import com.ecms.service.ProcedureEquiFileService;
import com.ecms.service.ProcedureEquiService;

import javax.annotation.Resource;

import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("procedureEquiFile")
public class ProcedureEquiFileController extends BaseController<ProcedureEquiFileEntity, ProcedureEquiFileInfo> {
	@Resource
	private ProcedureEquiService procedureEquiService;//需要调用格式化程序编号的方法，用于格式化文件编号
	
	@Resource
    private ProcedureEquiFileService procedureEquiFileService;

    @Override
    protected com.ecms.service.BaseService<ProcedureEquiFileEntity, ProcedureEquiFileInfo> getBaseService() {
        return procedureEquiFileService;
    }

    @RequestMapping("/detail")
    public Result<List<ProcedureEquiFileInfo>> detail(Integer id){
        ProcedureEquiFileInfo procedureEquiFileInfo = new ProcedureEquiFileInfo();
        procedureEquiFileInfo.setProcedureEquiId(id);
        List<ProcedureEquiFileInfo> procedureEquiFileInfoList = procedureEquiFileService.getListBy(procedureEquiFileInfo);
        for (int i = 0; i < procedureEquiFileInfoList.size(); i++) {//格式化文件编号
        	procedureEquiFileInfoList.get(i).setFileid(procedureEquiService.getprocedurenum()+"-"+(i+1));
		}
        
        return ResultUtils.success(procedureEquiFileInfoList);
    }
    /**获取下个文件编号
     * @return
     */
    @RequestMapping("/getnextfileid")
    public Result<String> getNextFileId(){
    	String FileId=procedureEquiFileService.getNextFileId();
        return ResultUtils.success(FileId);
    }
    
}