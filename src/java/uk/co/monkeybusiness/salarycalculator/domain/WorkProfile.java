package uk.co.monkeybusiness.salarycalculator.domain;

import org.joda.time.LocalTime;

public class WorkProfile {

    private LocalTime startTime;
    private LocalTime endTime;
    private Integer annualSalary;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

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

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isDayWorked(int dayOfWeek) {
        boolean worked = false;
        switch (dayOfWeek) {
            case 1: if (monday) worked = true; break;
            case 2: if (tuesday) worked = true; break;
            case 3: if (wednesday) worked = true; break;
            case 4: if (thursday) worked = true; break;
            case 5: if (friday) worked = true; break;
            case 6: if (saturday) worked = true; break;
            case 7: if (sunday) worked = true; break;
        }
        return worked;
    }
}
