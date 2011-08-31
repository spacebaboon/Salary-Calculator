package salary.calculator

class CalculatorController {

    def calculatorService

    def index = {

        def annualSalary = Integer.valueOf(params.annualSalary ?: '0')

        BigDecimal daily = calculatorService.currentDaily(annualSalary)
        BigDecimal monthly = calculatorService.currentMonthly(annualSalary)
        BigDecimal annual = calculatorService.currentAnnual(annualSalary)

        [daily:daily, monthly:monthly, annual: annual]
    }
}
