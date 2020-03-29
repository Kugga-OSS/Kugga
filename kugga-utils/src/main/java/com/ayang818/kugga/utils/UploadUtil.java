package com.ayang818.kugga.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/3/28 0:26
 **/
@Component
public class UploadUtil {
    @Value("${aliyun.oss.endpoint}")
    String endpoint;

    @Value("${aliyun.identity.accessKeyId}")
    String keyId;

    @Value("${aliyun.identity.accessKeySecret}")
    String keySecret;

    @Value("${aliyun.oss.bucketName}")
    String bucketName;

    @Value("${aliyun.oss.avatarFolder}")
    String avatarFolder;

    private volatile OSS ossClient;

    private static final String BASE_URL = "https://kugga-storage.oss-cn-hangzhou.aliyuncs.com/";

    public OSS getClient() {
        if (ossClient == null) {
            synchronized (this) {
                if (ossClient == null) {
                    ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
                }
            }
        }
        return ossClient;
    }

    public String upload(InputStream inputStream, String uploadType) {
        String filePath = avatarFolder + UUID.randomUUID() + ".jpg";
        try {
            getClient().putObject(bucketName, filePath, inputStream);
        } catch (OSSException | ClientException e) {
            return null;
        }
        return filePath;
    }

    public String getUrl(String avatarPath, String objectType) {
        return BASE_URL + avatarPath;
    }
}
