package com.ecms.test;

import com.aliyun.oss.OSSClient;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class OssTest {

    @Ignore
    @Test
    public void test() {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAIWg7S8mLfaYtH";
        String accessKeySecret = "4ds2SDujsNvmJ675tMP87sjdcH4MFp";
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String bucketName = "dev1-jobGuide";
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }
        String content = "Hello OSS";
        ossClient.putObject(bucketName, "hello.txt", new ByteArrayInputStream(content.getBytes()));
        ossClient.shutdown();
//        URL url = ossClient.generatePresignedUrl("ecms-nishang", "hello", new Date(System.currentTimeMillis() + 60000));
//        System.out.println(url.toString());
    }
}
