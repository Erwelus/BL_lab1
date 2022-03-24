package com.example.bl_lab1.repositories;

import com.example.bl_lab1.model.UserEntity;
import org.springframework.stereotype.Repository;


public interface UserRepository {
    UserEntity findByUsername(String username);
    void save(UserEntity user);
}
