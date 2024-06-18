package com.fireBnb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fireBnb.entity.PropertyUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${secrete.key}")
    private String algorithmkey;

    private Algorithm algorithm;
    @Value("${issuer}")
    private String issuer;
    @Value("${expiry.time}")
    private int expiryTime;
    private final static  String USER_NAME="username";
    @PostConstruct
    public void PostConstruct(){
        algorithm= Algorithm.HMAC256(algorithmkey);

    }
    public String generateToken(PropertyUser propertyUser){
       return JWT.create().withClaim(USER_NAME,propertyUser.getUsername()).
                withExpiresAt(new Date(System.currentTimeMillis())).withIssuer(issuer).sign(algorithm);
    }
    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();

    }
}
