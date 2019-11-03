package com.dawid.overtime.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    @NotNull
    @Pattern(regexp = "^[\\p{L}]+$")
    @Length(min = 1, max = 50)
    private String name;
    @NotNull
    @Pattern(regexp = "^[\\p{L}]+$")
    @Length(min = 1, max = 50)
    private String lastName;
    private String balance;
    private Set<OvertimeDto> overtime;
}
