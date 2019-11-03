package com.dawid.overtime.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Table(name = "EMPLOYEE")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[\\p{L}]+$")
    @Length(min = 1)
    @Length(max = 50)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Pattern(regexp = "^[\\p{L}]+$")
    @Length(min = 1)
    @Length(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "APPLICATION_USER_USERNAME")
    private ApplicationUserEntity applicationUser;

    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "CUSTOM_HOUR_STATISTIC_ID")
    private CustomHourStatisticEntity statistic;

    public CustomHourStatisticEntity initializeStats() {
        CustomHourStatisticEntity hourStatistic = this.getStatistic();
        if (hourStatistic == null) {
            this.statistic = new CustomHourStatisticEntity();
        }
        return getStatistic();
    }

    public EmployeeEntity(EmployeeEntity e) {
        this.id = e.getId();
        this.statistic = e.getStatistic();
        this.applicationUser = e.getApplicationUser();
        this.name = e.getName();
        this.lastName = e.getLastName();
    }
}
