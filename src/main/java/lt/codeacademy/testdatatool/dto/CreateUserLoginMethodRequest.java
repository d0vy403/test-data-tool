package lt.codeacademy.testdatatool.dto;

import jakarta.validation.constraints.NotNull;
import lt.codeacademy.testdatatool.entity.Method;

public record CreateUserLoginMethodRequest(
        @NotNull Method method,
        @NotNull Long userId
) {}
