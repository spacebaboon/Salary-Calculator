package salary.calculator;

import org.codehaus.groovy.runtime.typehandling.BigDecimalMath;
import org.joda.time.*;

import java.math.BigDecimal;

public class CalculatorJavaService implements ICalculatorJavaService {

    private LocalTime startTime = new LocalTime(9, 0);
    private LocalTime finishTime = new LocalTime(18, 0);
    private static final int TWO_DECIMAL_PLACES = 2;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static final int MONTHS_IN_YEAR = 12;

    public BigDecimal currentDaily(int annualSalary, LocalDateTime now) {
        LocalTime currentWorkTime = currentTimeWithinWorkHours(now.toLocalTime());
        Seconds secondsWorked = new Period(startTime, currentWorkTime).toStandardSeconds();
        float hoursWorked = secondsWorked.getSeconds() / 60 / 60;
        int daysInMonth = now.dayOfMonth().getMaximumValue();
        float fullDaily = fullDaily(annualSalary, daysInMonth);
        float hourlyRate = fullDaily / (finishTime.getHourOfDay() - startTime.getHourOfDay());
        float currentDaily = hoursWorked * hourlyRate;
        return round(currentDaily);
    }

    public BigDecimal currentMonthly(int annualSalary, LocalDateTime now) {
        LocalDate nowDate = now.toLocalDate();
        int daysWorkedThisMonth = nowDate.getDayOfMonth() - 1;
        int numberOfDaysThisMonth = nowDate.dayOfMonth().getMaximumValue();
        BigDecimal amountEarnedByFullDays = new BigDecimal(fullMonthly(annualSalary) * (daysWorkedThisMonth / numberOfDaysThisMonth));
        BigDecimal amountEarnedToday = currentDaily(annualSalary, now);
        return round(amountEarnedByFullDays.add(amountEarnedToday));
    }

    public BigDecimal currentAnnual(int annualSalary, LocalDateTime now) {
        int completeMonths = now.getMonthOfYear() - 1;
        BigDecimal completeMonthsEarnings = round(fullMonthly(annualSalary) * completeMonths);
        BigDecimal partialMonthsEarnings = currentMonthly(annualSalary, now);
        return round(completeMonthsEarnings.add(partialMonthsEarnings));
    }

    protected LocalTime currentTimeWithinWorkHours(LocalTime now) {
        if (now.compareTo(startTime) < 0) {
            return startTime;
        } else if (now.compareTo(finishTime) > 0) {
            return finishTime;
        } else {
            return now;
        }
    }

    protected float fullDaily(int annual, int daysInMonth) {
        return annual / daysInMonth;
    }

    protected float fullMonthly(int annual) {
        return annual / MONTHS_IN_YEAR;
    }

    protected BigDecimal round(double value) {
        return BigDecimal.valueOf(value).setScale(TWO_DECIMAL_PLACES, ROUNDING_MODE);
    }

    protected BigDecimal round(BigDecimal value) {
        return value.setScale(TWO_DECIMAL_PLACES, ROUNDING_MODE);
    }

}
