package com.example.server_controller;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param; 

public interface LinkRepository extends CrudRepository<Link, Integer> {
    @Query(value="SELECT s3url FROM links WHERE username = :username", nativeQuery=true) 
    String[] getUserImages(String username); 
}
