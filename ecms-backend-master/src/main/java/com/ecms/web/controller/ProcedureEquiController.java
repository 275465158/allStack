package com.ecms.web.controller;

import com.ecms.bean.CompanyInfo;
import com.ecms.bean.ProcedureEquiFileInfo;
import com.ecms.bean.ProcedureEquiInfo;
import com.ecms.bean.UserInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProcedureEquiEntity;
import com.ecms.service.BaseService;
import com.ecms.service.CompanyService;
import com.ecms.service.ProcedureEquiFileService;
import com.ecms.service.ProcedureEquiService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;
import static com.ecms.common.BusinessConstants.STATUS_INVALID;

@RestController
@RequestMapping("procedureEqui")
public class ProcedureEquiController extends BaseController<ProcedureEquiEntity, ProcedureEquiInfo> {
	@Resource
	private ProcedureEquiService procedureEquiService;

	@Resource
	private ProcedureEquiFileService procedureEquiFileService;

	@Resource
	private CompanyService companyService;

	@Override
	protected BaseService<ProcedureEquiEntity, ProcedureEquiInfo> getBaseService() {
		return procedureEquiService;
	}

	/**
	 * 查询所有设备程序
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param procedureEquiInfo
	 * @return
	 */
	@RequestMapping("/searchProcedurePager")
	public Result<Pager<ProcedureEquiInfo>> searchProcedurePager(int pageNum, int pageSize,
			ProcedureEquiInfo procedureEquiInfo) {
		ExchangeUtils.convertBlank2Null(procedureEquiInfo);
		// 查询status为1的数据
		procedureEquiInfo.setStatus(STATUS_DEFAULT);
		Pager<ProcedureEquiInfo> pager = procedureEquiService.getModelListByLike(pageNum, pageSize, procedureEquiInfo,
				false);
		for (ProcedureEquiInfo pe : pager.getList()) {
			ProcedureEquiFileInfo procedureEquiFileInfo = new ProcedureEquiFileInfo();
			procedureEquiFileInfo.setProcedureEquiId(pe.getId());
			procedureEquiFileInfo.setStatus(STATUS_DEFAULT);
			List<ProcedureEquiFileInfo> list = procedureEquiFileService.getListBy(procedureEquiFileInfo);
			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					pe.setProcedureEquiFileInfo(list.get(i));
				}
			}
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 获取list
	 * @return
	 */
	@RequestMapping("/searchList")
	public Result<List<ProcedureEquiInfo>> searchList(){
		ProcedureEquiInfo equiInfo = new ProcedureEquiInfo();
		equiInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		equiInfo.setStatus(STATUS_DEFAULT);
		List<ProcedureEquiInfo> list = procedureEquiService.getListBy(equiInfo);
		return ResultUtils.success();
	}

	/**
	 * 保存新增的设备程序数据
	 * 
	 * @param procedureEquiInfo
	 * @return
	 */
	@RequestMapping("/saveProcedure")
	public Result<Void> saveProcedure(ProcedureEquiInfo procedureEquiInfo, ProcedureEquiFileInfo fileInfo) {
		ProcedureEquiInfo tempinfo = new ProcedureEquiInfo();
		tempinfo.setProcedureName(procedureEquiInfo.getProcedureName());
		tempinfo.setStatus(STATUS_DEFAULT);
		List<ProcedureEquiInfo> searchNameList = procedureEquiService.getListBy(tempinfo);
		if (searchNameList.size() != 0) {
			return ResultUtils.failure("程序名称不可重复!");
		} else {
			procedureEquiService.save(procedureEquiInfo, fileInfo);
			return ResultUtils.success();
		}

	}

	@RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
	public void downloadfile(Integer id, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserInfo currentUser = LoginFilter.getCurrentUser();
		int companyId = currentUser.getCompanyId();
		id = Integer.valueOf(req.getParameter("id"));
		CompanyInfo companyInfo = companyService.getCompany(companyId);
		// 传入id，获取fileinfo 当时getBucketName方法是和公司id有关
		ProcedureEquiFileInfo pEquiFileInfo = procedureEquiFileService.getModel(id);
		String filename = pEquiFileInfo.getFileName();
		download(filename, req, resp);
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/removeProcedure")
	public Result<Void> removeProcedure(Integer id) {
		ProcedureEquiFileInfo procedureEquiFileInfo = new ProcedureEquiFileInfo();
		procedureEquiFileInfo.setProcedureEquiId(id);
		procedureEquiFileInfo.setStatus(STATUS_DEFAULT);
		// 根据设备程序id和状态为1查找当前关联的文件
		List<ProcedureEquiFileInfo> list = procedureEquiFileService.getListBy(procedureEquiFileInfo);
		if (list.size() != 0) {
			// 获取当前关联文件的id
			Integer fileId = list.get(0).getId();
			ProcedureEquiFileInfo pefi = new ProcedureEquiFileInfo();
			pefi.setId(fileId);
			pefi.setStatus(STATUS_INVALID);
			// 更新设备程序文件表的状态为0
			procedureEquiFileService.updateByPrimaryKeySelective(pefi);
		}
		// 更新设备程序表的状态为0
		ProcedureEquiInfo procedureEquiInfo = new ProcedureEquiInfo();
		procedureEquiInfo.setId(id);
		procedureEquiInfo.setStatus(STATUS_INVALID);
		procedureEquiService.updateByPrimaryKeySelective(procedureEquiInfo);
		return ResultUtils.success();
	}

	/**
	 * 根据id查找明细
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<ProcedureEquiInfo> detail(Integer id) {
		ProcedureEquiInfo procedureEquiInfo = procedureEquiService.getModel(id);
		
		return ResultUtils.success(procedureEquiInfo);
	}

	/**
	 * 暂时无用
	 * 
	 * @return 前端获取版本号
	 * @author pratice
	 * @date 2018年10月12日
	 */
	@RequestMapping("/getFileVersion")
	public Result<String> getFileVersion(Integer id) {
		// String version=procedureEquiService.findversion(id)+".0";
		return ResultUtils.success();
	}

	/**
	 * 获取数据库中最后一条数据的id，为添加下条数据做准备
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/getprocedurenum")
	public Result<String> getprocedurenum() {
		return ResultUtils.success(procedureEquiService.getprocedurenum());
	}
}