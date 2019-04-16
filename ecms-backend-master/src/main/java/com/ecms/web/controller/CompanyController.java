package com.ecms.web.controller;

import com.ecms.bean.CompanyInfo;
import com.ecms.bean.UserInfo;
import com.ecms.common.LogCapability;
import com.ecms.common.Result;
import com.ecms.service.CompanyService;
import com.ecms.utils.FileUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("company")
public class CompanyController implements LogCapability {

	private Logger logger = getLogger();
	@Value("${env}")
	private String env;
	@Resource
	private CompanyService companyService;

	@RequestMapping("save")
	public Result<Void> save(CompanyInfo companyInfo) {
		int companyId = LoginFilter.getCurrentUser().getCompanyId();
		companyInfo.setId(companyId);
		companyService.save(companyInfo);
		return ResultUtils.success();
	}

	@RequestMapping("get")
	public Result<CompanyInfo> get() {
		UserInfo currentUser = LoginFilter.getCurrentUser();
		int companyId = currentUser.getCompanyId();
		CompanyInfo companyInfo = companyService.getCompany(companyId);
		return ResultUtils.success(companyInfo);
	}

	@RequestMapping("uploadSimple")
	public Result<String> uploadSimple(MultipartFile file) {
		String result = "";
		if (file != null) {
			try {
				result = FileUtils.uploadFile(file, env, "company");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return ResultUtils.failure("上传文件失败:" + e.getMessage());
			}
		}
		return ResultUtils.success(result);
	}

	@RequestMapping("download")
	public void exportList(String filePath, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		response.setCharacterEncoding(request.getCharacterEncoding());
		if (filePath.endsWith(".jpeg") || filePath.endsWith(".jpg") || filePath.endsWith(".jpe")) {
			response.setContentType("image/jpeg");
		} else if (filePath.endsWith(".png")) {
			response.setContentType("image/png");
		} else {
			response.setContentType("application/octet-stream");
		}
		InputStream is = null;
		try {
			is = FileUtils.getInputStream(env, filePath);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
