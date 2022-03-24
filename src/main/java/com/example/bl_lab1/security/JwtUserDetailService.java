package com.example.bl_lab1.security;

import com.example.bl_lab1.model.UserEntity;
import com.example.bl_lab1.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException("User with username "+username+" does not exists");

        return JwtUserFactory.createJwtUser(user);
    }

}
