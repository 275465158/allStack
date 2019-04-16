package com.ecms.web.controller;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.excel.MaterialBomAuditingPassTemplate;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialBomService;
import com.ecms.utils.ExcelImportUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品物料扩展Controller（因为产品物料页面有两个excel导入导出，所以需要用扩展）
 * @author leisx
 * 2019/4/18
 */
@RestController
@RequestMapping("materialExtInfo")
public class MaterialExtController extends BaseController<MaterialBomEntity,MaterialBomInfo> {
	@Resource
	private MaterialBomService materialBomService;

	@Override
	protected BaseService<MaterialBomEntity,MaterialBomInfo> getBaseService() {
		return materialBomService;
	}

	/**
	 * 库存导入模板下载
	 * @author leisx
	 * @version 2019/04/04 16:53
	 */
	@Override
	protected List<MaterialBomAuditingPassTemplate> getTemplateForDownload() {
		List<MaterialBomAuditingPassTemplate> list = new ArrayList<>();
		list.add(new MaterialBomAuditingPassTemplate());
		return list;
	}


	/**
	 * 导入库存文件
	 * @param file
	 * @author leisx
	 * @version 2019/04/08 16:53
	 * @throws IOException
	 */
	@Override
	protected void uploadData(MultipartFile file) throws IOException {
		List<Object> list = ExcelImportUtil.importExcel(file.getInputStream(), MaterialBomAuditingPassTemplate.class);
		materialBomService.uploadAuditingPass(list);
	}


}

