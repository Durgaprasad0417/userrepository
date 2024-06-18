package com.fireBnb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PropertyUserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;
    private String password;

    private String userRole;
}
