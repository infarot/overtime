package com.dawid.overtime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
public class Shortage {

    private Duration amount;
    @Id
    private LocalDate shortageDate;
    private LocalDate catchUpDate;
    private String remarks;
    @ManyToOne
    private CustomHourStatistic customHourStatistic;

    public Duration getAmount() {
        return amount;
    }

    public void setAmount(Duration amount) {
        this.amount = amount;
    }

    public LocalDate getShortageDate() {
        return shortageDate;
    }

    public void setShortageDate(LocalDate shortageDate) {
        this.shortageDate = shortageDate;
    }

    public LocalDate getCatchUpDate() {
        return catchUpDate;
    }

    public void setCatchUpDate(LocalDate catchUpDate) {
        this.catchUpDate = catchUpDate;
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
}
