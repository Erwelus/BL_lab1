package com.example.bl_lab1.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@Data
@XmlRootElement
public class Users {
    private List<UserEntity> user;

    public Users(){
        user = new ArrayList<>();
    }
}
