package com.ecms.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.web.filter.LoginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static String endpoint;
    private static final String accessKeyId = "LTAIWg7S8mLfaYtH";
    private static final String accessKeySecret = "4ds2SDujsNvmJ675tMP87sjdcH4MFp";
    private static final String bucketNamePrefix = "ecms-nishang-";

    public static void setEndpoint(String url) {
        endpoint = url;
    }

    public static String uploadFile(MultipartFile file, String env, String filePath) {
        String key = "";
        try {
            key = filePath + LoginFilter.getCurrentUser().getCompanyId() + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String bucketName = bucketNamePrefix + env;
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
            }
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(file.getSize());
            ossClient.putObject(bucketName, key, file.getInputStream());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CommonBusinessException("上传文件失败");
        }
        return key;
    }

    public static String getFileUrl(String env, String key) {
        String bucketName = bucketNamePrefix + env;
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, new Date(System.currentTimeMillis() + 60000 * 60));
        if (url != null) {
            return url.toString().replace("hangzhou-internal.aliyuncs.com", "hangzhou.aliyuncs.com");
        } else {
            return "";
        }
    }

    public static InputStream getInputStream(String env, String key) {
        String bucketName = bucketNamePrefix + env;
        return getFile(bucketName, key);
    }

    public static InputStream getAppInstallPackage(String env, String key) {
        String bucketName = "ecms-app-" + env;
        return getFile(bucketName, key);
    }

    private static InputStream getFile(String bucketName, String key) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        if (ossObject != null) {
            return ossObject.getObjectContent();
        } else {
            return null;
        }
    }

}
