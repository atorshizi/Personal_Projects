package com.example.server_controller;

public class Creds{ 

    private String username; 
    private String password; 
    public Creds(){} 
    public Creds(String username, String password){ 
        this.username = username; 
        this.password = password; 
    } 
    public String getusername(){ 
        return this.username; 
    } 
    public String getpassword(){ 
        return this.password; 
    } 
}