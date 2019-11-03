package com.dawid.overtime.security.repository;

import com.dawid.overtime.entity.ApplicationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, Long> {
    Optional<ApplicationUserEntity> findByUsername(String username);
}
