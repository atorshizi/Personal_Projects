package com.example.server_controller;

public class Response{ 

    private String status; 
    private String token; 
    public Response(String status, String token){ 
        this.status = status; 
        this.token = token; 
    } 
    public String getstatus(){ 
        return this.status; 
    } 
    public String gettoken(){ 
        return this.token; 
    } 
}