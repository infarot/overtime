package com.dawid.overtime.security.service;

import com.dawid.overtime.entity.ApplicationUserEntity;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        ApplicationUserEntity applicationUser = applicationUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }

    public void save(ApplicationUserEntity applicationUser) {
        applicationUserRepository.save(applicationUser);
    }

    public boolean userExist(String username) {
        return applicationUserRepository.findByUsername(username).isPresent();
    }
}
