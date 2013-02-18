package salary.calculator

import org.joda.time.LocalDateTime
import uk.co.monkeybusiness.salarycalculator.service.CalculatorJavaService
import org.joda.time.LocalTime
import uk.co.monkeybusiness.salarycalculator.domain.WorkProfile

class CalculatorController {

    CalculatorJavaService calculatorJavaService
//    static LocalTime startTime = new LocalTime(9, 0)
//    static LocalTime endTime = new LocalTime(17, 30)

    def index = {
        [annualSalary: 0, startHour: 9, startMin: '00', endHour: 17, endMin: 30,
                mon: 'on', tue: 'on', wed: 'on', thu: 'on', fri: 'on']
    }

    def updateEarnings = {

        log.debug("received: ${params}")

        int annualSalary = Integer.valueOf(params.id ?: params.annualSalary ?: '0')
        def startTime = new LocalTime(params.startHour as int, params.startMin as int)
        def endTime = new LocalTime(params.endHour as int, params.endMin as int)
        def workProfile = new WorkProfile(annualSalary: annualSalary, startTime: startTime, endTime: endTime)

        LocalDateTime now = new LocalDateTime()
        BigDecimal daily = calculatorJavaService.currentDaily(workProfile, now)
        BigDecimal monthly = calculatorJavaService.currentMonthly(workProfile, now)
        BigDecimal annual = calculatorJavaService.currentAnnual(workProfile, now)

        def model = [daily: daily, monthly: monthly, annual: annual]

        render(template: "salaries", model: model)
    }

}
