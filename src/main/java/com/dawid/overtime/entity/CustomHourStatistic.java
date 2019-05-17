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
    private Duration balance;
    @OneToOne
    private Employee employee;
    @OneToMany(mappedBy = "customHourStatistic", cascade = CascadeType.ALL)
    private Set<Overtime> overtime;
    @OneToMany(mappedBy = "customHourStatistic", cascade = CascadeType.ALL)
    private Set<Shortage> shortage;

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

    public Set<Shortage> getShortage() {
        return shortage;
    }

    public void setShortage(Set<Shortage> shortage) {
        this.shortage = shortage;
    }

    public Set<Overtime> initializeOvertime() {
        Set<Overtime> overtimeSet = this.getOvertime();
        if (overtimeSet == null) {
            this.overtime = new HashSet<>();
        }
        return getOvertime();
    }

    public Set<Shortage> initializeShortage() {
        Set<Shortage> shortageSet = this.getShortage();
        if (shortageSet == null) {
            this.shortage = new HashSet<>();
        }
        return getShortage();
    }
}
