package uk.co.monkeybusiness.salarycalculator.domain

class WorkProfileTest extends GroovyTestCase {


    void testIsDayWorked() {
        WorkProfile profile = new WorkProfile()
        profile.monday = true
        profile.tuesday = false

        assert profile.isDayWorked(1)
        assert ! profile.isDayWorked(2)
    }
}
