package salary.calculator

import org.joda.time.LocalDateTime

class CalculatorController {

    def calculatorJavaService

    def index = {
        [daily: 0, monthly: 0, annual: 0]
    }

    def updateEarnings = {

        def annualSalary = Integer.valueOf(params.id ?: '0')
        LocalDateTime now = new LocalDateTime()

        BigDecimal daily = calculatorJavaService.currentDaily(annualSalary, now)
        BigDecimal monthly = calculatorJavaService.currentMonthly(annualSalary, now)
        BigDecimal annual = calculatorJavaService.currentAnnual(annualSalary, now)

        def model = [annualSalary:annualSalary, daily:daily, monthly:monthly, annual:annual]

        render(template: "salaries", model: model)
    }
}
