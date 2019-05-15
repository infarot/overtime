package com.dawid.overtime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Entity
public class CustomHourStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Duration balance;
    @OneToOne
    private Employee employee;
    @OneToMany(mappedBy = "customHourStatistic", cascade = CascadeType.ALL)
    private List<Overtime> overtime;
    @OneToMany(mappedBy = "customHourStatistic", cascade = CascadeType.ALL)
    private List<Shortage> shortage;

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Overtime> getOvertime() {
        return overtime;
    }

    public void setOvertime(List<Overtime> overtime) {
        this.overtime = overtime;
    }

    public List<Shortage> getShortage() {
        return shortage;
    }

    public void setShortage(List<Shortage> shortage) {
        this.shortage = shortage;
    }
}
