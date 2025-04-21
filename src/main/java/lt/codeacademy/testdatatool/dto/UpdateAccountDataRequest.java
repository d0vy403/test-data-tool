package lt.codeacademy.testdatatool.dto;

import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record UpdateAccountDataRequest(
    @Length(max = 50) String type,
    @PositiveOrZero BigDecimal balance,
    @Length(min = 3, max = 10) String currency,
    Boolean blocked) {}
