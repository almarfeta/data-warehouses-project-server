package com.example.data_warehouses_project_server.dwh;

import java.time.LocalDate;

import com.example.data_warehouses_project_server.domain.olap.entity.DimTimeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class DimTimeResponse {

    private Long timeKey;
    private LocalDate dateValue;
    private Integer day;
    private Integer month;
    private Integer year;
    private String weekDay;
    private String monthName;

    private DimTimeResponse(Long timeKey, LocalDate dateValue, Integer day, Integer month, Integer year, String weekDay, String monthName) {
        this.timeKey = timeKey;
        this.dateValue = dateValue;
        this.day = day;
        this.month = month;
        this.year = year;
        this.weekDay = weekDay;
        this.monthName = monthName;
    }

    public static DimTimeResponse fromEntity(DimTimeEntity dimTime) {
        return new DimTimeResponse(
                dimTime.getTimeKey(),
                dimTime.getDateValue(),
                dimTime.getDay(),
                dimTime.getMonth(),
                dimTime.getYear(),
                dimTime.getWeekDay(),
                dimTime.getMonthName()
        );
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
