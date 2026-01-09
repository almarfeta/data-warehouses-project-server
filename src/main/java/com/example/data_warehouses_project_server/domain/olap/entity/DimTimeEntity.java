package com.example.data_warehouses_project_server.domain.olap.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "dimTime")
@Table(name = "dim_time")
public class DimTimeEntity {

    @Id
    @Column(name = "time_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeKey;

    @Column(name = "date_value")
    private LocalDate dateValue;

    @Column(name = "day")
    private Integer day;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "week_day", length = 10)
    private String weekDay;

    @Column(name = "month_name", length = 10)
    private String monthName;

    public DimTimeEntity() {
    }

    public DimTimeEntity(LocalDate dateValue, Integer day, Integer month, Integer year, String weekDay, String monthName) {
        this.dateValue = dateValue;
        this.day = day;
        this.month = month;
        this.year = year;
        this.weekDay = weekDay;
        this.monthName = monthName;
    }

    public Long getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(Long timeKey) {
        this.timeKey = timeKey;
    }

    public LocalDate getDateValue() {
        return dateValue;
    }

    public void setDateValue(LocalDate dateValue) {
        this.dateValue = dateValue;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
