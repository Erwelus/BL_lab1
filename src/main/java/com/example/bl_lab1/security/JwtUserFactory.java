package com.example.bl_lab1.security;

import com.example.bl_lab1.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    public static JwtUser createJwtUser(UserEntity user){
        return new JwtUser(user.getId(),
                user.getUsername(),
                user.getPassword(),
                collectRoles(user.getRole()));
    }

    private static List<GrantedAuthority> collectRoles(String roles){
        String[] roleArray = roles.split(",");
        return Arrays.stream(roleArray).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
