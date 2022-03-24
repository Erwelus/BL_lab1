package com.example.bl_lab1.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement
public class UserEntity {
    @Id
    private Integer id;
    private String username;
    private String password;
    private String refreshToken;
    private String role;

    public UserEntity(Integer id, String username, String password, String role){
        this.id = id;
        this.username=username;
        this.password=password;
        this.role=role;
    }

//    public UserEntity(Integer id, String username, String password, String refreshToken, String role){
//        this.id=id;
//        this.username=username;
//        this.password=password;
//        this.refreshToken=refreshToken;
//        this.role=role;
//    }

}
