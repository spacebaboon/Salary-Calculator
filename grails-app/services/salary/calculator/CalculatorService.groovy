package salary.calculator

import org.joda.time.*
import java.math.MathContext

class CalculatorService {

    static transactional = true
    def startTime = new LocalTime(9, 0)
    def finishTime = new LocalTime(18, 0)
    def twoDecimalPlaces = new MathContext(2)

    BigDecimal currentDaily(Integer annualSalary, LocalDateTime now) {
        def currentWorkTime = currentTimeWithinWorkHours(now.toLocalTime())
        def secondsWorked = new Period(startTime, currentWorkTime).toStandardSeconds()
        def hoursWorked = secondsWorked.getSeconds() / 60 / 60
        def daysInMonth = now.dayOfMonth().getMaximumValue()
        BigDecimal fullDaily = fullDaily(annualSalary, daysInMonth)
        def hourlyRate = fullDaily / (finishTime.getHourOfDay() - startTime.getHourOfDay())
        def currentDaily = hoursWorked * hourlyRate
        return new BigDecimal(currentDaily, twoDecimalPlaces)
    }

    LocalTime currentTimeWithinWorkHours(LocalTime now) {
        if (now.compareTo(startTime) < 0){
            return startTime
        } else if (now.compareTo(finishTime) > 0) {
            return finishTime
        } else {
            return now
        }
    }

    BigDecimal currentMonthly(int annualSalary, LocalDateTime now) {
        def nowDate = now.toLocalDate()
        def daysWorkedThisMonth = nowDate.getDayOfMonth() - 1
        def numberOfDaysThisMonth = nowDate.dayOfMonth().getMaximumValue()
        def amountEarnedByFullDays = daysWorkedThisMonth / numberOfDaysThisMonth * fullMonthly(annualSalary)
        
        def amountEarnedToday = currentDaily(annualSalary, now)
        long currentMonthly =  amountEarnedByFullDays + amountEarnedToday
        return new BigDecimal(currentMonthly, twoDecimalPlaces)

    }

    BigDecimal currentAnnual(int annualSalary, LocalDateTime now) {
        return annualSalary
    }

    BigDecimal fullDaily(int annual, int daysInMonth) {
        return new BigDecimal(fullMonthly(annual) / daysInMonth, twoDecimalPlaces)
    }

    BigDecimal fullMonthly(int annual) {
        return annual / 12
    }
}
