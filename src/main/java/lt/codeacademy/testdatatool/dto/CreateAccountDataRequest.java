package lt.codeacademy.testdatatool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateAccountDataRequest(
    @Length(max = 50) @NotBlank String accountNumber,
    @Length(max = 50) @NotBlank String type,
    @PositiveOrZero @NotNull BigDecimal balance,
    @Length(min = 3, max = 10) @NotBlank String currency,
    boolean blocked,
    @NotNull Long userId) {}
