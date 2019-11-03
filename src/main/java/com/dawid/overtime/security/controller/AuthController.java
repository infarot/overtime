package com.dawid.overtime.security.controller;

import com.dawid.overtime.dto.ApplicationUserDto;
import com.dawid.overtime.security.exception.UsernameAlreadyTakenException;
import com.dawid.overtime.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userService;
    private final ApplicationUserMapper applicationUserMapper;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid ApplicationUserDto user) {
        if (userService.userExist(user.getUsername())) {
            throw new UsernameAlreadyTakenException("Username is already taken!");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(applicationUserMapper.toEntity(user));
        }
    }
}
