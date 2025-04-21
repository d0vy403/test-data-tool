package lt.codeacademy.testdatatool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lt.codeacademy.testdatatool.entity.Channel;
import lt.codeacademy.testdatatool.entity.Environment;
import org.hibernate.validator.constraints.Length;

public record CreateUserDataRequest(
        @Length(min = 8, max = 50) @NotBlank String userName,
        @Length(min = 8, max = 50) @NotBlank String password,
        @NotNull Channel channel,
        @NotNull Environment environment
        ) {}
