package com.dawid.overtime.security.service;

import com.dawid.overtime.security.entity.ApplicationUser;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public ApplicationUser findByUsername(String username) {
        return applicationUserRepository.findByUsername(username);
    }
}
