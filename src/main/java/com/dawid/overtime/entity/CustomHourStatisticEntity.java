package com.dawid.overtime.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "CUSTOM_HOUR_STATISTIC")
public class CustomHourStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Transient
    @Column(name = "BALANCE")
    private Duration balance;

    @OneToOne(mappedBy = "statistic", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "customHourStatistic", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<OvertimeEntity> overtime;

    public Set<OvertimeEntity> initializeOvertime() {
        Set<OvertimeEntity> overtimeSet = this.getOvertime();
        if (overtimeSet == null) {
            this.overtime = new HashSet<>();
        }
        return getOvertime();
    }
}
