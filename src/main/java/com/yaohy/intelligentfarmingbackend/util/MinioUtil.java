package com.yaohy.intelligentfarmingbackend.util;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class MinioUtil {

    private static MinioClient minioClient;

    private static final String defaultBucket = "intelligent-farming";

    private MinioUtil() {

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    static {
        //2KuZihRYrGOeaCVfyydk
        //8cZmPRA2hWIPTnyyXZ0c
        try {
            minioClient = new MinioClient(
                    "",
                    "",
                    "");


            checkBucket(defaultBucket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkBucket(String bucketName){
        try {
            if (!minioClient.bucketExists(bucketName))
                minioClient.makeBucket(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String uploadJpg(MultipartFile file,String name){
        try {
            checkBucket(defaultBucket);

            PutObjectOptions putObjectOptions = new PutObjectOptions(
                    (long) -1, 5L * 1024 * 1024 * 1024);
            putObjectOptions.setContentType("image/jpeg");

            minioClient.putObject(
                    defaultBucket,
//                    file.getOriginalFilename(),
                    name,
                    file.getInputStream(),
                    putObjectOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String deleteJpg(String name){
        try {
            checkBucket(defaultBucket);
            minioClient.removeObject(defaultBucket,name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String uploadPng(MultipartFile file,String name){
        try {
            checkBucket(defaultBucket);

            PutObjectOptions putObjectOptions = new PutObjectOptions(
                    (long) -1, 5L * 1024 * 1024 * 1024);
            putObjectOptions.setContentType("image/png");

            minioClient.putObject(
                    defaultBucket,
//                    file.getOriginalFilename(),
                    name,
                    file.getInputStream(),
                    putObjectOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String upload(String bucketName,File file){


        return null;
    }

}
