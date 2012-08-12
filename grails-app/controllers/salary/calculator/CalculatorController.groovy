package salary.calculator

import org.joda.time.LocalDateTime
import uk.co.monkeybusiness.salarycalculator.service.service.CalculatorJavaService
import org.joda.time.LocalTime
import uk.co.monkeybusiness.salarycalculator.domain.WorkProfile

class CalculatorController {

    CalculatorJavaService calculatorJavaService
    static LocalTime startTime = new LocalTime(9, 0)
    static LocalTime endTime = new LocalTime(17, 30)

    def index = {
        int annualSalary = Integer.valueOf(params.annualSalary ?: '0')
        log.debug "annual salary: ${annualSalary}"

        def workProfile = new WorkProfile(annualSalary: annualSalary, startTime: startTime, endTime: endTime)

        getSalaryFigures(workProfile)
    }

    def updateEarnings = {

        int annualSalary = Integer.valueOf(params.id ?: params.annualSalary ?: '0')
        log.debug "annual salary: ${annualSalary}"
        def workProfile = new WorkProfile(annualSalary: annualSalary, startTime: startTime, endTime: endTime)

        LinkedHashMap<String, BigDecimal> model = getSalaryFigures(workProfile)

        render(template: "salaries", model: model)
    }

    private LinkedHashMap<String, BigDecimal> getSalaryFigures(WorkProfile workProfile) {
        LocalDateTime now = new LocalDateTime()

        BigDecimal daily = calculatorJavaService.currentDaily(workProfile, now)
        BigDecimal monthly = calculatorJavaService.currentMonthly(workProfile, now)
        BigDecimal annual = calculatorJavaService.currentAnnual(workProfile, now)

        def model = [annualSalary: workProfile.annualSalary, daily: daily, monthly: monthly, annual: annual]
        model
    }
}
