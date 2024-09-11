package com.example.server_controller;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "links") 
public class Link{ 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Integer id; 
    private String username; 
    private String s3url; 
    private Link(){ 

    } 
    public Link(String username, String s3url){ 
        this.username = username; 
        this.s3url = s3url; 
    } 
    public String getusername(){ 
        return this.username; 
    } 
    public String gets3url(){ 
        return this.s3url; 
    } 
    public Integer getid(){
        return this.id; 
    }
}