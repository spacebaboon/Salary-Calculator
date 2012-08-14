package uk.co.monkeybusiness.salarycalculator.domain

import org.joda.time.LocalDateTime
import org.joda.time.LocalDate

class WorkProfileTest extends GroovyTestCase {


    void testIsDayWorked() {
        WorkProfile profile = new WorkProfile()
        profile.monday = true
        profile.tuesday = false

        assert profile.isDayWorked(1)
        assert ! profile.isDayWorked(2)
    }
}
