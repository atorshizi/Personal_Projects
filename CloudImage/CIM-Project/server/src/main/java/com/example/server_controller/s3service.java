package com.example.server_controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest; 

import io.jsonwebtoken.io.IOException;

import java.time.Duration;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service 
public class s3service {
    @Value("${aws.region}") 
    private String awsRegion; 

    private S3Client s3client;
    private S3Presigner presigner; 
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 
    
    @Value("${aws.s3.bucket}")
    private String bucketName;

    public s3service(S3Client s3client, S3Presigner presigner) {
        this.s3client = s3client;
        this.presigner = presigner; 
    }

    private String generateUniqueKey(String originalFileName) {
        // Extract the file extension
        String fileExtension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = originalFileName.substring(i + 1);
        } 
        // Generate a UUID and append the file extension
        return UUID.randomUUID().toString() + "." + fileExtension;
    } 

    public String uploadFile(String username, String filename, MultipartFile file) throws IOException, S3Exception, AwsServiceException, SdkClientException, java.io.IOException {
        String genUUID = username + "/" + generateUniqueKey(filename);

        PutObjectRequest putReq = PutObjectRequest.builder()
            .bucket(bucketName) // Replace with your bucket name
            .key(genUUID)
            .build();

        PutObjectResponse putObjectResult = s3client.putObject(putReq, RequestBody.fromInputStream(file.getInputStream(),file.getSize()));

        logger.info(putObjectResult.toString());

        return genUUID;
    } 
    private String getPresignedURL(String keyName){ 
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(objectRequest)
                .build();
        PresignedGetObjectRequest presignedRequest = this.presigner.presignGetObject(presignRequest);
        logger.info("Presigned URL: [{}]", presignedRequest.url().toString());
        logger.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

        return presignedRequest.url().toExternalForm();
    } 
    public String[] getUsersURLs(String[] keynames){  
        String presignedURLs[] = new String[keynames.length];  
        for (int i=0;i<keynames.length;i++){ 
            presignedURLs[i] = getPresignedURL(keynames[i]); 
        }
        return presignedURLs; 
    }

}