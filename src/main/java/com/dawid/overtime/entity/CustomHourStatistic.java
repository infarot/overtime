package com.dawid.overtime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CustomHourStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Duration balance;
    @OneToOne
    private Employee employee;
    @OneToMany(mappedBy = "customHourStatistic", cascade = CascadeType.ALL)
    private Set<Overtime> overtime;

    public Long getId() {
        return id;
    }

    public Duration getBalance() {
        return balance;
    }

    public void setBalance(Duration balance) {
        this.balance = balance;
    }

    @JsonIgnore
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Overtime> getOvertime() {
        return overtime;
    }

    public void setOvertime(Set<Overtime> overtime) {
        this.overtime = overtime;
    }

    public Set<Overtime> initializeOvertime() {
        Set<Overtime> overtimeSet = this.getOvertime();
        if (overtimeSet == null) {
            this.overtime = new HashSet<>();
        }
        return getOvertime();
    }
}
