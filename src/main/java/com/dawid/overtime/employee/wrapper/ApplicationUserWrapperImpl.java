package com.dawid.overtime.employee.wrapper;

import com.dawid.overtime.entity.ApplicationUserEntity;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationUserWrapperImpl implements ApplicationUserWrapper {

    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserWrapperImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Optional<ApplicationUserEntity> findByUsername(String username) {
        return applicationUserRepository.findByUsername(username);
    }

}
