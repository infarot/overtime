package com.dawid.overtime.security.service;

import com.dawid.overtime.security.entity.ApplicationUser;

public interface ApplicationUserService {
    ApplicationUser findByUsername(String username);
}
