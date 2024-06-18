package com.fireBnb.config;

import com.fireBnb.entity.PropertyUser;
import com.fireBnb.repository.PropertyUserRepository;
import com.fireBnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.SimpleTimeZone;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            System.out.println(token);
            String username = jwtService.getUsername(token);
            Optional<PropertyUser> obUsername = propertyUserRepository.findByUsername(username);
            if (obUsername.isPresent()){
                PropertyUser user = obUsername.get();
                //setting to session
                //evaru login chesaro chudataniki (dinini session) ani antaru
               UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(username,null,Collections.singleton(new SimpleGrantedAuthority(user.getUserRole())));
               authentication.setDetails(new WebAuthenticationDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
