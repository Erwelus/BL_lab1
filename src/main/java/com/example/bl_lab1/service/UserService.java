package com.example.bl_lab1.service;

import com.example.bl_lab1.model.UserEntity;


public interface UserService {
    UserEntity register(UserEntity user);
    UserEntity findByUsername(String username);
    String getCurrentUserName();
}
