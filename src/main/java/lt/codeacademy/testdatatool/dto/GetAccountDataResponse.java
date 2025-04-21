package lt.codeacademy.testdatatool.dto;

import java.math.BigDecimal;

public record GetAccountDataResponse(
    Long id,
    String accountNumber,
    String type,
    BigDecimal balance,
    String currency,
    boolean blocked,
    Long userId) {}
