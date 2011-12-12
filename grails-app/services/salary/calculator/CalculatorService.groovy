package salary.calculator

import org.joda.time.*

class CalculatorService {

    static transactional = true
    def startTime = new LocalTime(9, 0)
    def finishTime = new LocalTime(18, 0)
    def TWO_DECIMAL_PLACES = 2
    def ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN

    BigDecimal currentDaily(Integer annualSalary, LocalDateTime now) {
        def currentWorkTime = currentTimeWithinWorkHours(now.toLocalTime())
        def secondsWorked = new Period(startTime, currentWorkTime).toStandardSeconds()
        def hoursWorked = secondsWorked.getSeconds() / 60 / 60
        def daysInMonth = now.dayOfMonth().getMaximumValue()
        BigDecimal fullDaily = fullDaily(annualSalary, daysInMonth)
        def hourlyRate = fullDaily / (finishTime.getHourOfDay() - startTime.getHourOfDay())
        BigDecimal currentDaily = hoursWorked * hourlyRate
        return round(currentDaily)
    }

    BigDecimal currentMonthly(int annualSalary, LocalDateTime now) {
        def nowDate = now.toLocalDate()
        def daysWorkedThisMonth = nowDate.getDayOfMonth() - 1
        def numberOfDaysThisMonth = nowDate.dayOfMonth().getMaximumValue()
        def amountEarnedByFullDays = daysWorkedThisMonth / numberOfDaysThisMonth * fullMonthly(annualSalary)

        def amountEarnedToday = currentDaily(annualSalary, now)
        def currentMonthly =  amountEarnedByFullDays + amountEarnedToday
        return round(currentMonthly)

    }

    BigDecimal currentAnnual(int annualSalary, LocalDateTime now) {
        int completeMonths = now.monthOfYear - 1
        float completeMonthsEarnings = completeMonths * fullMonthly(annualSalary)
        float partialMonthsEarnings = currentMonthly(annualSalary, now)
        return round(completeMonthsEarnings + partialMonthsEarnings)
    }

    protected currentTimeWithinWorkHours(LocalTime now) {
        if (now.compareTo(startTime) < 0){
            return startTime
        } else if (now.compareTo(finishTime) > 0) {
            return finishTime
        } else {
            return now
        }
    }

    protected fullDaily(int annual, int daysInMonth) {
        return round(fullMonthly(annual) / daysInMonth)
    }

    protected fullMonthly(int annual) {
        return annual / 12
    }

    protected round(BigDecimal value) {
        return value.setScale(TWO_DECIMAL_PLACES, ROUNDING_MODE)
    }
}
