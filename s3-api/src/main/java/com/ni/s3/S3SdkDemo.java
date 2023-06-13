package com.ni.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * S3对象存储官方SDK实现
 */
@SuppressWarnings("deprecation")
public class S3SdkDemo {
    private static final Logger logger = LoggerFactory.getLogger(S3SdkDemo.class);

    private AmazonS3 s3client;
    private String endpoint = "http://docker03:19000";
    private String accessKey = "admin";
    private String secretKey = "Ab123456";

    public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
        S3SdkDemo s3SdkDemo = new S3SdkDemo();
        s3SdkDemo.init();
        logger.info("ma-system bucket是否存在: {}", s3SdkDemo.bucketExists("ma-system"));

        File file = new File("./s3-api/src/main/resources/log4j.properties");
        s3SdkDemo.upload("ma-system", file.getName(), new FileInputStream(file));
    }

    public void init() throws MalformedURLException {
        URL endpointUrl = new URL(endpoint);
        String protocol = endpointUrl.getProtocol();
        int port = endpointUrl.getPort() == -1 ? endpointUrl.getDefaultPort() : endpointUrl.getPort();

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSignerOverride("S3SignerType");
        clientConfig.setProtocol(Protocol.valueOf(protocol.toUpperCase()));

        // 禁用证书检查，避免https自签证书校验失败
        System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");
        // 屏蔽 AWS 的 MD5 校验，避免校验导致的下载抛出异常问题
        System.setProperty("com.amazonaws.services.s3.disableGetObjectMD5Validation", "true");
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        // 创建 S3Client 实例
        AmazonS3 s3client = new AmazonS3Client(awsCredentials, clientConfig);
        s3client.setEndpoint(endpointUrl.getHost() + ":" + port);
        s3client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
        this.s3client = s3client;
    }

    public boolean createBucket(String bucket) {
        try {
            s3client.createBucket(bucket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteBucket(String bucket) {
        try {
            s3client.deleteBucket(bucket);
            logger.info("删除bucket[{}]成功", bucket);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean bucketExists(String bucket) {
        try {
            return s3client.doesBucketExist(bucket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void upload(String bucket, String objectId, InputStream input) {
        try {
            // 创建文件上传的元数据
            ObjectMetadata meta = new ObjectMetadata();
            // 设置文件上传长度
            meta.setContentLength(input.available());
            // 上传
            s3client.putObject(bucket, objectId, input, meta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InputStream download(String bucket, String objectId) {
        try {
            S3Object o = s3client.getObject(bucket, objectId);
            return o.getObjectContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void download(String bucket, String objectId, OutputStream out) {
        S3Object o = s3client.getObject(bucket, objectId);
        try (InputStream in = o.getObjectContent()) {
            IOUtils.copyLarge(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean existObject(String bucket, String objectId) {
        try {
            return s3client.doesObjectExist(bucket, objectId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteObject(String bucket, String objectId) {
        try {
            s3client.deleteObject(bucket, objectId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close() {
        s3client = null;
    }
}
