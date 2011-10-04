package salary.calculator

import org.joda.time.LocalDateTime

class CalculatorController {

    def calculatorService

    def index = {

        def annualSalary = Integer.valueOf(params.annualSalary ?: '0')

        LocalDateTime now = new LocalDateTime()
        BigDecimal daily = calculatorService.currentDaily(annualSalary, now)
        BigDecimal monthly = calculatorService.currentMonthly(annualSalary, now)
        BigDecimal annual = calculatorService.currentAnnual(annualSalary, now)

        [annualSalary:annualSalary, daily:daily, monthly:monthly, annual:annual]
    }
}
