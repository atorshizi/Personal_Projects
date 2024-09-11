package com.example.server_controller;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users") 
public class User{ 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Integer id; 
    private String username; 
    private String password; 
    private User(){ 

    } 
    public User(String username, String password){ 
        this.username = username; 
        this.password = password; 
    } 
    public String getusername(){ 
        return this.username; 
    } 
    public String getpassword(){ 
        return this.password; 
    } 
    public Integer getid(){
        return this.id; 
    }
}