package salary.calculator

import grails.test.*
import org.joda.time.LocalDateTime
import org.joda.time.DateTime
import org.joda.time.DateMidnight
import org.joda.time.LocalTime
import org.joda.time.LocalDate
import java.security.Security

class CalculatorServiceTests extends GrailsUnitTestCase {

    private CalculatorService calculatorService

    protected void setUp() {
        super.setUp()
        calculatorService = new CalculatorService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCurrentTimeWithinWorkHoursReturnsStartTimeIfBefore() {
        def eightAm = new LocalTime(8, 0)
        def expected = new LocalTime(9, 0)
        def actual = calculatorService.currentTimeWithinWorkHours(eightAm)
        assert expected == actual
    }

    void testCurrentTimeWithinWorkHoursReturnsEndTimeIfAfter() {
        def sevenPm = new LocalTime(19, 0)
        def expected = new LocalTime(18, 0)
        def actual = calculatorService.currentTimeWithinWorkHours(sevenPm)
        assert expected == actual
    }

    void testFullMonthlyReturnsCorrectValue() {
        int annualSalary = 24000
        float monthly = calculatorService.fullMonthly(annualSalary)

        assert monthly == 2000
    }

    void testDailyAt8amReturnsZero() {
        int annualSal = 125000
        def eightOClock = new LocalDateTime(2011, 1, 1, 8, 0)
        println "time: $eightOClock"

        float dailySoFar = calculatorService.currentDaily(annualSal, eightOClock)
        assert 0 == dailySoFar
    }

    void testDailyAt9amReturnsZero() {
        int annualSal = 125000
        def nineOClock = new LocalDateTime(2011, 1, 1, 9, 0)
        println "time: $nineOClock"

        float dailySoFar = calculatorService.currentDaily(annualSal, nineOClock)
        assert 0 == dailySoFar
    }

    void testDailyAt6pmReturnsFullDailyRate() {
        int annualSal = 60000
        def sixPm = new LocalDateTime(2011, 1, 1, 18, 0)
        float fullDaily = calculatorService.fullDaily(annualSal, 31) // days in Jan
        float dailySoFar = calculatorService.currentDaily(annualSal, sixPm)
        assert fullDaily == dailySoFar
    }

    void testDailyAt7pmReturnsFullDailyRate() {
        int annualSal = 60000
        def sevenPm = new LocalDateTime(2011, 1, 1, 19, 0)
        float fullDaily = calculatorService.fullDaily(annualSal, 31)
        float dailySoFar = calculatorService.currentDaily(annualSal, sevenPm)
        assert fullDaily == dailySoFar
    }

    void testDailyAt1230ReturnsHalfDailyRate() {
        int annualSal = 50000
        def oneThirty = new LocalDateTime(2011, 1, 1, 13, 30)
        float fullDaily = calculatorService.fullDaily(annualSal, 31)
        float dailySoFar = calculatorService.currentDaily(annualSal, oneThirty)
        assert fullDaily / 2 == dailySoFar
    }

    void testMonthlyAt9amFirstAprilReturnsZero() {
        int annualSal = 30000
        def aprilFirst = new LocalDateTime(2011, 4, 1, 9, 0, 0)
        float monthlySoFar = calculatorService.currentMonthly(annualSal, aprilFirst)
        assert 0 == monthlySoFar
    }

    void testMonthlyOn31Jan6pmReturnsFullMonthlyRate() {
        int annualSal = 24000
        def january31SixPm = new LocalDateTime(2011, 1, 31, 18, 0, 0)
        float monthlySoFar = calculatorService.currentMonthly(annualSal, january31SixPm)
        assert annualSal / 12 == monthlySoFar
    }

    void testMonthlyOn28Feb11pmReturnsFullMonthlyRate() {
        int annualSal = 60000
        def january31SixPm = new LocalDateTime(2011, 2, 28, 23, 0, 0)
        float monthlySoFar = calculatorService.currentMonthly(annualSal, january31SixPm)
        assert annualSal / 12 == monthlySoFar
    }

    void testAnnualAt9amJan1ReturnsZero() {
        int annualSal = 100000
        LocalDateTime nineAmJan1 = new LocalDateTime(2012, 1, 1, 9, 0, 0)
        def annualSoFar = calculatorService.currentAnnual(annualSal, nineAmJan1)
        assert 0 == annualSoFar
    }

    void testAnnual6pmDecember31ReturnsFullAnnual() {
        int annualSal = 375000
        LocalDateTime sixPmDec31 = new LocalDateTime(2000, 12, 31, 18, 0, 0)
        def annualSoFar = calculatorService.currentAnnual(annualSal, sixPmDec31)
        assert annualSal == annualSoFar
    }
    
}
