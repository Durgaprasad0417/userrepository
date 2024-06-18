package com.fireBnb.service;

import com.fireBnb.dto.LoginDto;
import com.fireBnb.dto.PropertyUserDto;
import com.fireBnb.entity.PropertyUser;
import com.fireBnb.repository.PropertyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final PropertyUserRepository repository;
    private final JWTService jwtService; // Ensure this is autowired correctly

    @Override
    public PropertyUserDto addUser(PropertyUserDto dto) {
        PropertyUser user = new PropertyUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10))); // Hash password
        user.setUserRole(dto.getUserRole());
        user.setUsername(dto.getUsername());
        PropertyUser save = repository.save(user);
        PropertyUserDto puo = new PropertyUserDto();
        puo.setId(save.getId());
        puo.setFirstName(save.getFirstName());
        puo.setLastName(save.getLastName());
        puo.setEmail(save.getEmail());
        puo.setPassword(save.getPassword());
        puo.setUserRole(save.getUserRole());
        puo.setUsername(save.getUsername());
        return puo;
    }

    @Override
    public String verifyLogin(@RequestBody LoginDto loginDto) {
        Optional<PropertyUser> opUsername = repository.findByUsername(loginDto.getUsername());
        if (opUsername.isPresent()) {
            PropertyUser propertyUser = opUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword())) {
                return jwtService.generateToken(propertyUser); // Generate token on successful login
            }
        }
        return null;
    }
}
