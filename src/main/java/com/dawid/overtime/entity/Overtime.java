package com.dawid.overtime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Overtime {

    private Duration amount;
    @Id
    private LocalDate overtimeDate;
    private LocalDate pickUpDate;
    private String remarks;
    @ManyToOne
    private CustomHourStatistic customHourStatistic;

    public Duration getAmount() {
        return amount;
    }

    public void setAmount(Duration amount) {
        this.amount = amount;
    }

    public LocalDate getOvertimeDate() {
        return overtimeDate;
    }

    public void setOvertimeDate(LocalDate overtimeDate) {
        this.overtimeDate = overtimeDate;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonIgnore
    public CustomHourStatistic getCustomHourStatistic() {
        return customHourStatistic;
    }

    public void setCustomHourStatistic(CustomHourStatistic customHourStatistic) {
        this.customHourStatistic = customHourStatistic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Overtime)) return false;
        Overtime overtime = (Overtime) o;
        return overtimeDate.equals(overtime.overtimeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(overtimeDate);
    }
}
