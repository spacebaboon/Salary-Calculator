package uk.co.monkeybusiness.salarycalculator.service;

import org.joda.time.LocalDateTime;
import uk.co.monkeybusiness.salarycalculator.domain.WorkProfile;

import java.math.BigDecimal;

public interface ICalculatorJavaService {

    BigDecimal currentDaily(WorkProfile profile, LocalDateTime now);

    BigDecimal currentMonthly(WorkProfile profile, LocalDateTime now);

    BigDecimal currentAnnual(WorkProfile profile, LocalDateTime now);
}
