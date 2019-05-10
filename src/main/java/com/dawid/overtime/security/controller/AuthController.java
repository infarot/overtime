package com.dawid.overtime.security.controller;

import com.dawid.overtime.security.entity.ApplicationUser;
import com.dawid.overtime.security.exception.UsernameAlreadyTakenException;
import com.dawid.overtime.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userService;

    @Autowired
    public AuthController(BCryptPasswordEncoder passwordEncoder, UserDetailsServiceImpl userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid ApplicationUser user) {
        if (userService.userExist(user.getUsername())) {
            throw new UsernameAlreadyTakenException("Username is already taken!");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
        }
    }
}
