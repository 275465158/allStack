package com.ecms.web.controller;

import com.ecms.common.Result;
import com.ecms.dal.entity.VersionEntity;
import com.ecms.service.VersionService;
import com.ecms.utils.FileUtils;
import com.ecms.utils.ResultUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("version")
public class VersionController {

    private static final Logger logger = LoggerFactory.getLogger(VersionController.class);
    @Resource
    private VersionService versionService;
    @Value("${env}")
    private String env;

    @RequestMapping("/android/latest")
    public Result<String> getAndroidLatestVersion() {
        VersionEntity version = versionService.getLatestVersion(1);
        return ResultUtils.success(version.getAppVersion());
    }

    @RequestMapping("/android/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        InputStream is = null;
        try {
            VersionEntity version = versionService.getLatestVersion(1);
            String fileName = version.getName();
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            is = FileUtils.getAppInstallPackage(env, version.getId() + "-" + version.getName());
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
