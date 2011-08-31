package salary.calculator

import org.joda.time.*

class CalculatorService {

    static transactional = true
    def startTime = new LocalTime(9, 0)
    def finishTime = new LocalTime(18, 0)

    BigDecimal currentDaily(Integer annualSalary, LocalDateTime now) {
        def currentWorkTime = currentTimeWithinWorkHours(now.toLocalTime())
        def secondsWorked = new Period(startTime, currentWorkTime).toStandardSeconds()
        def hoursWorked = secondsWorked.getSeconds() / 60 / 60
        def daysInMonth = now.toLocalDate().monthOfYear().getMaximumValue()
        def hourlyRate = fullDaily(annualSalary, daysInMonth) / (finishTime.getHourOfDay() - startTime.getHourOfDay())
        def currentDaily = hoursWorked * hourlyRate
        return currentDaily
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
        return amountEarnedByFullDays + amountEarnedToday
    }

    BigDecimal currentAnnual(int annualSalary, LocalDateTime now) {
        return annualSalary
    }

    BigDecimal fullDaily(int annual, int daysInMonth) {
        return fullMonthly(annual) / daysInMonth
    }

    BigDecimal fullMonthly(int annual) {
        return annual / 12
    }
}
