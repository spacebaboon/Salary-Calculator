package uk.co.monkeybusiness.salarycalculator.service.service;

import org.joda.time.*;
import uk.co.monkeybusiness.salarycalculator.domain.WorkProfile;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorJavaService implements ICalculatorJavaService {

    private static final int TWO_DECIMAL_PLACES = 2;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static final int MONTHS_IN_YEAR = 12;

    public BigDecimal currentDaily(WorkProfile profile, LocalDateTime now) {
        int dayOfWeek = now.getDayOfWeek();
        boolean workedToday = profile.isDayWorked(dayOfWeek);
        if (!workedToday) {
            return new BigDecimal(0);
        }
        LocalTime currentWorkTime = currentTimeWithinWorkHours(profile, now.toLocalTime());
        Seconds secondsWorked = new Period(profile.getStartTime(), currentWorkTime).toStandardSeconds();
        float hoursWorked = (float)secondsWorked.getSeconds() / 60 / 60;
        int daysInMonth = now.dayOfMonth().getMaximumValue();
        BigDecimal fullDaily = fullDaily(profile.getAnnualSalary(), daysInMonth);
        BigDecimal hourlyRate = fullDaily.divide(new BigDecimal(profile.getEndTime().getHourOfDay() - profile.getStartTime().getHourOfDay()), MathContext.DECIMAL64);
        BigDecimal currentDaily = hourlyRate.multiply(new BigDecimal(hoursWorked));
        return round(currentDaily);
    }

    public BigDecimal currentMonthly(WorkProfile profile, LocalDateTime now) {
        LocalDate nowDate = now.toLocalDate();
        int daysWorkedThisMonth = nowDate.getDayOfMonth() - 1;
        int numberOfDaysThisMonth = nowDate.dayOfMonth().getMaximumValue();
        float proportionOfMonthWorked = new Float(daysWorkedThisMonth) / numberOfDaysThisMonth;
        BigDecimal amountEarnedByFullDays = fullMonthly(profile.getAnnualSalary()).multiply(new BigDecimal(proportionOfMonthWorked));
        BigDecimal amountEarnedToday = currentDaily(profile, now);
        return round(amountEarnedByFullDays.add(amountEarnedToday));
    }

    public BigDecimal currentAnnual(WorkProfile profile, LocalDateTime now) {
        int completeMonths = now.getMonthOfYear() - 1;
        BigDecimal completeMonthsEarnings = round(fullMonthly(profile.getAnnualSalary()).multiply(new BigDecimal(completeMonths)));
        BigDecimal partialMonthsEarnings = currentMonthly(profile, now);
        return round(completeMonthsEarnings.add(partialMonthsEarnings));
    }

    protected LocalTime currentTimeWithinWorkHours(WorkProfile profile, LocalTime now) {
        if (now.compareTo(profile.getStartTime()) < 0) {
            return profile.getStartTime();
        } else if (now.compareTo(profile.getEndTime()) > 0) {
            return profile.getEndTime();
        } else {
            return now;
        }
    }

    protected BigDecimal fullDaily(int annual, int daysInMonth) {
        return fullMonthly(annual).divide(new BigDecimal(daysInMonth), MathContext.DECIMAL64);
    }

    protected BigDecimal fullMonthly(int annual) {
        return new BigDecimal(annual).divide(new BigDecimal(MONTHS_IN_YEAR), MathContext.DECIMAL64);
    }

    protected BigDecimal round(BigDecimal value) {
        return value.setScale(TWO_DECIMAL_PLACES, ROUNDING_MODE);
    }

}
