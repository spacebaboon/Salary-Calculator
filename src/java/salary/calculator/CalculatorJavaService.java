package salary.calculator;

import org.codehaus.groovy.runtime.typehandling.BigDecimalMath;
import org.joda.time.*;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorJavaService implements ICalculatorJavaService {

    private LocalTime startTime = new LocalTime(9, 0);
    private LocalTime finishTime = new LocalTime(18, 0);
    private static final int TWO_DECIMAL_PLACES = 2;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static final int MONTHS_IN_YEAR = 12;

    public BigDecimal currentDaily(int annualSalary, LocalDateTime now) {
        LocalTime currentWorkTime = currentTimeWithinWorkHours(now.toLocalTime());
        Seconds secondsWorked = new Period(startTime, currentWorkTime).toStandardSeconds();
        float hoursWorked = (float)secondsWorked.getSeconds() / 60 / 60;
        int daysInMonth = now.dayOfMonth().getMaximumValue();
        BigDecimal fullDaily = fullDaily(annualSalary, daysInMonth);
        BigDecimal hourlyRate = fullDaily.divide(new BigDecimal(finishTime.getHourOfDay() - startTime.getHourOfDay()), MathContext.DECIMAL64);
        BigDecimal currentDaily = hourlyRate.multiply(new BigDecimal(hoursWorked));
        return round(currentDaily);
    }

    public BigDecimal currentMonthly(int annualSalary, LocalDateTime now) {
        LocalDate nowDate = now.toLocalDate();
        int daysWorkedThisMonth = nowDate.getDayOfMonth() - 1;
        int numberOfDaysThisMonth = nowDate.dayOfMonth().getMaximumValue();
        float proportionOfMonthWorked = new Float(daysWorkedThisMonth) / numberOfDaysThisMonth;
        BigDecimal amountEarnedByFullDays = fullMonthly(annualSalary).multiply(new BigDecimal(proportionOfMonthWorked));
        BigDecimal amountEarnedToday = currentDaily(annualSalary, now);
        return round(amountEarnedByFullDays.add(amountEarnedToday));
    }

    public BigDecimal currentAnnual(int annualSalary, LocalDateTime now) {
        int completeMonths = now.getMonthOfYear() - 1;
        BigDecimal completeMonthsEarnings = round(fullMonthly(annualSalary).multiply(new BigDecimal(completeMonths)));
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
