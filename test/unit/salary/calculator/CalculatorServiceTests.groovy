package salary.calculator

import grails.test.*
import org.joda.time.LocalDateTime

import org.joda.time.LocalTime

import uk.co.monkeybusiness.salarycalculator.domain.WorkProfile
import uk.co.monkeybusiness.salarycalculator.service.service.CalculatorJavaService

class CalculatorServiceTests extends GrailsUnitTestCase {

    private CalculatorJavaService calculatorService
    private WorkProfile workProfile;
    def eightAm = new LocalTime(8, 0)
    def nineAm = new LocalTime(9, 0)
    def sixPm = new LocalTime(18, 0)


    protected void setUp() {
        super.setUp()
        calculatorService = new CalculatorJavaService()
        workProfile = new WorkProfile(25000, nineAm, sixPm)

    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCurrentTimeWithinWorkHoursReturnsStartTimeIfBefore() {
        def expected = new LocalTime(9, 0)
        def actual = calculatorService.currentTimeWithinWorkHours(workProfile, eightAm)
        assert expected == actual
    }

    void testCurrentTimeWithinWorkHoursReturnsEndTimeIfAfter() {
        def sevenPm = new LocalTime(19, 0)
        def expected = new LocalTime(18, 0)
        def actual = calculatorService.currentTimeWithinWorkHours(workProfile, sevenPm)
        assert expected == actual
    }

    void testFullMonthlyReturnsCorrectValue() {
        workProfile.setAnnualSalary(24000)
        BigDecimal monthly = calculatorService.fullMonthly(workProfile.getAnnualSalary())
        assert monthly == 2000
    }

    void testDailyAt8amReturnsZero() {
        workProfile.setAnnualSalary(125000)
        def eightOClock = new LocalDateTime(2011, 1, 1, 8, 0)
        BigDecimal dailySoFar = calculatorService.currentDaily(workProfile, eightOClock)
        assert 0 == dailySoFar
    }

    void testDailyAt9amReturnsZero() {
        workProfile.setAnnualSalary(125000)
        def nineOClock = new LocalDateTime(2011, 1, 1, 9, 0)
        BigDecimal dailySoFar = calculatorService.currentDaily(workProfile, nineOClock)
        assert 0 == dailySoFar
    }

    void testDailyAt6pmReturnsFullDailyRate() {
        workProfile.setAnnualSalary(60000)
        def sixPm = new LocalDateTime(2011, 1, 1, 18, 0)
        BigDecimal fullDaily = calculatorService.round(calculatorService.fullDaily(workProfile.getAnnualSalary(), 31)) // days in Jan
        BigDecimal dailySoFar = calculatorService.currentDaily(workProfile, sixPm)
        assert fullDaily == dailySoFar
    }

    void testDailyAt7pmReturnsFullDailyRate() {
        workProfile.setAnnualSalary(60000)
        def sevenPm = new LocalDateTime(2011, 1, 1, 19, 0)
        BigDecimal fullDaily = calculatorService.round(calculatorService.fullDaily(workProfile.getAnnualSalary(), 31))
        BigDecimal dailySoFar = calculatorService.currentDaily(workProfile, sevenPm)
        assert fullDaily == dailySoFar
    }

    void testDailyAt1330ReturnsHalfDailyRate() {
        workProfile.setAnnualSalary(50000)
        def oneThirty = new LocalDateTime(2011, 1, 1, 13, 30)
        BigDecimal fullDaily = calculatorService.fullDaily(workProfile.getAnnualSalary(), 31)
        BigDecimal dailySoFar = calculatorService.currentDaily(workProfile, oneThirty)
        assert new BigDecimal(fullDaily / 2).setScale(2, BigDecimal.ROUND_HALF_EVEN) == dailySoFar
    }

    void testMonthlyAt9amFirstAprilReturnsZero() {
        workProfile.setAnnualSalary(30000)
        def aprilFirst = new LocalDateTime(2011, 4, 1, 9, 0, 0)
        BigDecimal monthlySoFar = calculatorService.currentMonthly(workProfile, aprilFirst)
        assert 0 == monthlySoFar
    }

    void testMonthlyOn31Jan6pmReturnsFullMonthlyRate() {
        workProfile.setAnnualSalary(24000)
        def january31SixPm = new LocalDateTime(2011, 1, 31, 18, 0, 0)
        BigDecimal monthlySoFar = calculatorService.currentMonthly(workProfile, january31SixPm)
        assert workProfile.getAnnualSalary() / 12 == monthlySoFar
    }

    void testMonthlyOn28Feb11pmReturnsFullMonthlyRate() {
        workProfile.setAnnualSalary(60000)
        def january31SixPm = new LocalDateTime(2011, 2, 28, 23, 0, 0)
        BigDecimal monthlySoFar = calculatorService.currentMonthly(workProfile, january31SixPm)
        assert workProfile.getAnnualSalary() / 12 == monthlySoFar
    }

    void testAnnualAt9amJan1ReturnsZero() {
        workProfile.setAnnualSalary(100000)
        LocalDateTime nineAmJan1 = new LocalDateTime(2012, 1, 1, 9, 0, 0)
        def annualSoFar = calculatorService.currentAnnual(workProfile, nineAmJan1)
        assert 0 == annualSoFar
    }

    void testAnnual6pmDecember31ReturnsFullAnnual() {
        workProfile.setAnnualSalary(120000)
        LocalDateTime sixPmDec31 = new LocalDateTime(2000, 12, 31, 18, 0, 0)
        BigDecimal annualSoFar = calculatorService.currentAnnual(workProfile, sixPmDec31)
        assert workProfile.getAnnualSalary() == annualSoFar
    }

    void testRoundToTwoDecimalPlaces() {
        BigDecimal bigNumber = new BigDecimal("123456789.002")
        assert 123456789 == calculatorService.round(bigNumber)
    }

    void testThatFullDailyPayOnSaturdayShouldBeZeroIfNotWorked() {
        workProfile.setSaturday(false)
        workProfile.setAnnualSalary(60000)
        def sevenPm = new LocalDateTime(2012, 8, 11, 19, 0) // Saturday
        BigDecimal dailySoFar = calculatorService.currentDaily(workProfile, sevenPm)
        assert 0 == dailySoFar
    }

    void testThatFullDailyPayOnSundayShouldBeFullIfWorked() {
        workProfile.setSunday(true)
        workProfile.setAnnualSalary(60000)
        def sevenPm = new LocalDateTime(2012, 8, 12, 19, 0) // Sunday
        BigDecimal fullDaily = calculatorService.round(calculatorService.fullDaily(workProfile.getAnnualSalary(), 31))
        BigDecimal dailySoFar = calculatorService.currentDaily(workProfile, sevenPm)
        assert fullDaily == dailySoFar
    }

    void testThatFullMonthsPayShouldBeTheSameIfOneDayOrSevenWorked() {

    }


}
