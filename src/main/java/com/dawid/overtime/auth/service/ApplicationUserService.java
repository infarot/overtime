package com.dawid.overtime.auth.service;

import com.dawid.overtime.auth.entity.ApplicationUser;

public interface ApplicationUserService {
    ApplicationUser findByUsername(String username);
}
