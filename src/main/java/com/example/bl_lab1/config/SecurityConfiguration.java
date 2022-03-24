package com.example.bl_lab1.config;

import com.example.bl_lab1.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfiguration(JwtFilter jwtFilter){
        this.jwtFilter=jwtFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String LOGIN_ENDPOINT = "/api/auth/**";
    private static final String LOGIN_PAGE = "/application/login/";
    private static final String REFRESH_ENDPOINT = "/api/refresh/**";
    private static final String ARTICLE_CONTENT ="/section/**";
    private static final String SAVE_VERSION_ENDPOINT ="/version/save";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(LOGIN_PAGE).permitAll()
                .antMatchers(REFRESH_ENDPOINT).permitAll()
                .antMatchers(ARTICLE_CONTENT).permitAll()
                .antMatchers(SAVE_VERSION_ENDPOINT).permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
