package com.example.server_controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class s3Configs {

    @Value("${aws.access.key}")
    private String awsAccessKey;

    @Value("${aws.secret.key}")
    private String awsSecretKey; 

    @Value("${aws.region}") 
    private String awsRegion; 

    @Bean
    public S3Client s3client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(awsAccessKey, awsSecretKey); 
        return S3Client.builder()
            .region(Region.of(awsRegion))  // Use the region from properties
            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
            .build();
    } 

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(awsAccessKey, awsSecretKey); 
        return S3Presigner.builder()
                            .region(Region.of(awsRegion))
                            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                            .build(); 
    } 
}