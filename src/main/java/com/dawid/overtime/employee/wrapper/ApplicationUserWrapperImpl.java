package com.dawid.overtime.employee.wrapper;

import com.dawid.overtime.security.entity.ApplicationUser;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationUserWrapperImpl implements ApplicationUserWrapper {

    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserWrapperImpl(ApplicationUserRepository applicationUserRepository){
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Optional<ApplicationUser> findByUsername(String username) {
        return applicationUserRepository.findByUsername(username);
    }
}
