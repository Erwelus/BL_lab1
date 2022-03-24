package com.example.bl_lab1.controllers;

import com.example.bl_lab1.dto.UserDto;
import com.example.bl_lab1.model.UserEntity;
import com.example.bl_lab1.security.JwtProvider;
import com.example.bl_lab1.service.UserService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService){
        this.authenticationManager=authenticationManager;
        this.jwtProvider=jwtProvider;
        this.userService=userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto userDTO){
        try {
            String username = userDTO.getUsername();
            UserEntity user = userService.findByUsername(username);

            if (user == null) throw new UsernameNotFoundException("User with username " + username + " not found");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userDTO.getPassword()));
            String token = jwtProvider.createToken(username);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("refreshToken", user.getRefreshToken());
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (UsernameNotFoundException ex){
            Map<Object, Object> response = new HashMap<>();
            response.put("description", "User with username " + userDTO.getUsername() + " not found");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }catch (AuthenticationException ex){
            Map<Object, Object> response = new HashMap<>();
            response.put("description", "Wrong password");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
        @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDTO){

        try{
            String username = userDTO.getUsername();
            if(userService.findByUsername(username)!=null){
                throw new NonUniqueResultException("User with such username has been already registered");
            }
            UserEntity user = userService.register(new UserEntity(userDTO.getId(), username, userDTO.getPassword(), userDTO.getRole()));
            String token = jwtProvider.createToken(username);
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("refreshToken", user.getRefreshToken());
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (NonUniqueResultException | IncorrectResultSizeDataAccessException ex){
            Map<Object, Object> response = new HashMap<>();
            response.put("description", "User with username "+userDTO.getUsername()+" has already been registered");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}
