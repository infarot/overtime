package com.dawid.overtime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
public class Shortage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Duration amount;
    private LocalDate shortageDate;
    private LocalDate catchUpDate;
    private String remarks;
    @ManyToOne
    private CustomHourStatistic customHourStatistic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
