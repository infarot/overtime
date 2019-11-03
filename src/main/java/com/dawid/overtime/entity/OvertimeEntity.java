package com.dawid.overtime.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Table(name = "OVERTIME")
public class OvertimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AMOUNT")
    private Duration amount;

    @Column(name = "OVERTIME_DATE")
    private LocalDate overtimeDate;

    @Column(name = "PICK_UP_DATE")
    private LocalDate pickUpDate;

    @Column(name = "REMARKS")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "CUSTOM_HOUR_STATISTIC_ID")
    private CustomHourStatisticEntity customHourStatistic;
}
