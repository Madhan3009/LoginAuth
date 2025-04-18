package com.LoginAuth.demo.controller;
import com.LoginAuth.demo.Entity.User;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.LoginAuth.demo.dto.UserDto;
import com.LoginAuth.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthenticationController {
    public UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {       //Model works like a shopping cart that fills its cart and take it to the template(ie HTML)
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String save(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model) {
        User user = userService.findUserbyEmail(userDto.getEmail());
        if(user == null && user.getEmail() != null && !user.getEmail().isEmpty()) {
            bindingResult.rejectValue("Email",null,"There is already an account registered with this email");
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success=true";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> userDtos = userService.findAllUser();
        model.addAttribute("users", userDtos);
        return "users";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
