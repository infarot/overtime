package com.dawid.overtime.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
public class ApplicationUser {
    @NotNull
    @Id
    @Size(min = 4, max = 20, message = "Username must be more than 4 characters")
    private String username;
    @NotNull
    @Size(min = 8, max = 100, message = "Password must be more than 8 characters")
    private String password;
    @OneToMany(mappedBy = "applicationUser", fetch = FetchType.LAZY)
    private List<Employee> employees;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationUser)) return false;
        ApplicationUser that = (ApplicationUser) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
