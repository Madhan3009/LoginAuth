package com.LoginAuth.demo.service;

import com.LoginAuth.demo.Entity.User;
import com.LoginAuth.demo.dto.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserbyEmail(String email);
    List<UserDto> findAllUser();
}
