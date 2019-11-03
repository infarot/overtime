package com.dawid.overtime.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "APPLICATION_USER")
public class ApplicationUserEntity {
    @NotNull
    @Id
    @Size(min = 4, max = 20, message = "Username must be more than 4 characters")
    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @Size(min = 8, max = 100, message = "Password must be more than 8 characters")
    @Column(name = "PASSWORD")
    private String password;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "applicationUser", fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;
}
