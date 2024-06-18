package com.fireBnb.service;

import com.fireBnb.dto.LoginDto;
import com.fireBnb.dto.PropertyUserDto;

public interface UserService {
    PropertyUserDto addUser(PropertyUserDto dto);

    String verifyLogin(LoginDto loginDto);
}
