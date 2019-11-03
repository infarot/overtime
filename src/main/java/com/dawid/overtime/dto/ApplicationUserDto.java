package com.dawid.overtime.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ApplicationUserDto {
    @NotNull
    @Id
    @Size(min = 4, max = 20, message = "Username must be more than 4 characters")
    private String username;
    @NotNull
    @Size(min = 8, max = 100, message = "Password must be more than 8 characters")
    private String password;
    @Email
    @NotNull
    private String email;
}
