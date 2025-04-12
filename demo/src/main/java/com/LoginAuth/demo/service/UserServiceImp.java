package com.LoginAuth.demo.service;

import com.LoginAuth.demo.Entity.Role;
import com.LoginAuth.demo.Entity.User;
import com.LoginAuth.demo.dto.UserDto;
import com.LoginAuth.demo.repository.RoleRepo;
import com.LoginAuth.demo.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    public UserRepo UserRepo;
    public RoleRepo RoleRepo;
    public PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepo UserRepo, RoleRepo RoleRepo, PasswordEncoder passwordEncoder) {
        this.UserRepo = UserRepo;
        this.RoleRepo = RoleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDto userDto)
    {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = RoleRepo.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleexists();
        }
        user.setRoles(List.of(role));
        UserRepo.save(user);
    }

    private Role checkRoleexists(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return role;
    }


    public User findUserbyEmail(String email) {
        return UserRepo.findByEmail(email);
    }

    public List<UserDto> findAllUser() {
        List<User> users = UserRepo.findAll();
        return users.stream().map(user -> convertEntitytoDto(user)).collect(Collectors.toList());
    }

    private UserDto convertEntitytoDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
