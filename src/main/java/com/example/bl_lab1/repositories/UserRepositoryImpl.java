package com.example.bl_lab1.repositories;

import com.example.bl_lab1.model.UserEntity;
import com.example.bl_lab1.model.Users;
import com.example.bl_lab1.service.impl.XMLUtilImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final XMLUtilImpl xmlUtil;
    private final String xmlPath = "C:\\Users\\user\\IdeaProjects\\BL_lab1\\src\\main\\resources\\users.xml";

    public UserRepositoryImpl(XMLUtilImpl xmlUtil) {
        this.xmlUtil = xmlUtil;
    }

    @Override
    public UserEntity findByUsername(String username) {
        Users users = (Users)xmlUtil.getEntity(Users.class, "userList", xmlPath);
        if (users==null || users.getUser()==null) return null;
        List<UserEntity> userEntities = users.getUser();
        for (UserEntity cur: userEntities) {
            if (cur.getUsername().equals(username)) return cur;
        }
        return null;
    }

    @Override
    public void save(UserEntity user) {
        Users users = (Users)xmlUtil.getEntity(Users.class, "users", xmlPath);
        if (users==null) users = new Users();
        users.getUser().add(user);
        xmlUtil.saveEntity(users.getUser(), xmlPath);
    }
}
