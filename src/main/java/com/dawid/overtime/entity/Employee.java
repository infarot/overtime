package com.dawid.overtime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Pattern(regexp = "^[\\p{L}]+$")
    @Length(min = 1)
    @Length(max = 50)
    private String name;
    @NotNull
    @Pattern(regexp = "^[\\p{L}]+$")
    @Length(min = 1)
    @Length(max = 50)
    private String lastName;
    @ManyToOne
    private ApplicationUser applicationUser;

    @JsonIgnore
    public String getApplicationUserUsername() {
        return applicationUser.getUsername();
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName);
    }
}
