package com.LoginAuth.demo.service;

import com.LoginAuth.demo.Entity.Role;
import com.LoginAuth.demo.Entity.User;
import com.LoginAuth.demo.repository.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class Security implements UserDetailsService {
    public UserRepo userRepo;
    public Security(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        else{
            return new org.springframework.security.core.userdetails.User(
                   user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles())
            );
        }
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        Collection<? extends GrantedAuthority> maproles = roles.stream()
                                                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                                                .collect(Collectors.toList());
    return maproles;
    }
}
