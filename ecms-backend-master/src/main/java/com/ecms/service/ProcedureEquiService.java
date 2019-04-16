package com.ecms.service;

import com.ecms.bean.ProcedureEquiFileInfo;
import com.ecms.bean.ProcedureEquiInfo;
import com.ecms.dal.entity.ProcedureEquiEntity;
import com.ecms.dal.entity.ProcedureEquiFileEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProcedureEquiFileManager;
import com.ecms.manager.ProcedureEquiManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.FileUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

@Service
public class ProcedureEquiService extends BaseService<ProcedureEquiEntity, ProcedureEquiInfo> {
	@Resource
	private ProcedureEquiManager procedureEquiManager;
	@Resource
	private ProcedureEquiFileManager procedureEquiFileManager;

	@Override
	protected BaseManager<ProcedureEquiEntity> getManager() {
		return procedureEquiManager;
	}

	@Value("${env}")
	private String env;

	
	/**格式化程序编号
	 * @return
	 * @author pratice
	 * @date 2018年10月19日
	 */
	public String getprocedurenum() {
		if (procedureEquiManager.getProcedureNum() == null) {
			return "PR0001";
		} else if ((procedureEquiManager.getProcedureNum()) < 10) {
			return "PR000" + ((procedureEquiManager.getProcedureNum() + 1));
		} else if ((procedureEquiManager.getProcedureNum()) < 100) {
			return "PR00" + (procedureEquiManager.getProcedureNum() + 1);
		} else if ((procedureEquiManager.getProcedureNum()) < 1000) {
			return "PR0" + (procedureEquiManager.getProcedureNum() + 1);
		} else if ((procedureEquiManager.getProcedureNum()) < 10000) {
			return "PR" + (procedureEquiManager.getProcedureNum() + 1);
		}

		return null;

	}

	@Transactional
	public void save(ProcedureEquiInfo info, ProcedureEquiFileInfo fileInfo) {
		ProcedureEquiEntity equiEntity = getEntityAfterSaveOrUpdate(info);
		//先增加主表
		int procedureEquiId = equiEntity.getId();
		procedureEquiFileManager.deleteByPrimaryKey(procedureEquiId);
		//删除后再添加，方便修改
		fileInfo.setProcedureEquiId(procedureEquiId);
		//设置制作人
		fileInfo.setCompileMan(LoginFilter.getCurrentUser().getUserName());
		//设置版本号
		fileInfo.setFileVersion(findversion(procedureEquiId));
		ProcedureEquiFileEntity procedureEquiFileEntity = ExchangeUtils.exchangeObject(fileInfo,
				ProcedureEquiFileEntity.class);
		procedureEquiFileManager.insertBy(procedureEquiFileEntity);
	}

	@Override
	public String uploadFileSimple(MultipartFile file) {
		return FileUtils.uploadFile(file, env, "procedure-file/");
	}

	@Override
	public InputStream getFileInputStream(String filePath) {
		return FileUtils.getInputStream(env, filePath);
	}


	public int findversion(int id) {
		return procedureEquiFileManager.findversion(id)+1;
	}
}