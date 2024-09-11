package com.example.server_controller;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param; 

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE username = :username AND password = :password", nativeQuery = true)
    User login(@Param("username") String username, @Param("password") String password); 

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User checkUsername(@Param("username") String username); 
}
