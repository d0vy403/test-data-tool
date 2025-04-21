package lt.codeacademy.testdatatool.dto;

import lt.codeacademy.testdatatool.entity.Channel;
import lt.codeacademy.testdatatool.entity.Environment;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDataRequest(
    @Length(min = 8, max = 50) String userName,
    @Length(min = 8, max = 50) String password,
    Channel channel,
    Environment environment) {}
