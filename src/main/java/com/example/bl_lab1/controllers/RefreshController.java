package com.example.bl_lab1.controllers;

import com.example.bl_lab1.dto.RefreshDto;
import com.example.bl_lab1.model.UserEntity;
import com.example.bl_lab1.security.JwtProvider;
import com.example.bl_lab1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/refresh/")
public class RefreshController {
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public RefreshController(JwtProvider jwtProvider, UserService userService){
        this.jwtProvider=jwtProvider;
        this.userService=userService;
    }

    @PostMapping("/token")
    public ResponseEntity refreshToken(@RequestBody RefreshDto refreshDto){
        String username = new String(Base64.getDecoder().decode(refreshDto.getRefreshToken())).split("&")[1];
        UserEntity user = userService.findByUsername(username);
        Map<Object, Object> response = new HashMap<>();

        if(user == null) throw new UsernameNotFoundException("User with username " + username + " not found");

        if(user.getRefreshToken().equals(refreshDto.getRefreshToken())) {
            String token = jwtProvider.createToken(username);
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.put("username", username);
            response.put("refreshToken", user.getRefreshToken());
            response.put("token", token);
        }else throw new BadCredentialsException("Invalid refresh token");

        return ResponseEntity.ok(response);
    }
}
