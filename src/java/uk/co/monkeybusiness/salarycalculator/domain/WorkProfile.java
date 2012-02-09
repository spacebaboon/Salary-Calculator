package uk.co.monkeybusiness.salarycalculator.domain;

import org.joda.time.LocalTime;

public class WorkProfile {

    private LocalTime startTime;
    private LocalTime endTime;
    private Integer annualSalary;

    public WorkProfile() {
    }

    public WorkProfile(Integer annualSalary, LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.annualSalary = annualSalary;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(Integer annualSalary) {
        this.annualSalary = annualSalary;
    }
}
