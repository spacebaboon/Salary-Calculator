package salary.calculator.service;

import org.joda.time.LocalDateTime;
import salary.calculator.domain.WorkProfile;

import java.math.BigDecimal;

public interface ICalculatorJavaService {

    BigDecimal currentDaily(WorkProfile profile, LocalDateTime now);

    BigDecimal currentMonthly(WorkProfile profile, LocalDateTime now);

    BigDecimal currentAnnual(WorkProfile profile, LocalDateTime now);
}
