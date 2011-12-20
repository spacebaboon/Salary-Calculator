package salary.calculator;

import org.joda.time.LocalDateTime;

import java.math.BigDecimal;

public interface ICalculatorJavaService {

    BigDecimal currentDaily(int annualSalary, LocalDateTime now);

    BigDecimal currentMonthly(int annualSalary, LocalDateTime now);

    BigDecimal currentAnnual(int annualSalary, LocalDateTime now);
}
