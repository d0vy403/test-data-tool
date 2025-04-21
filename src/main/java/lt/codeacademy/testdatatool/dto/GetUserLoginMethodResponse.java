package lt.codeacademy.testdatatool.dto;

import lt.codeacademy.testdatatool.entity.Method;

public record GetUserLoginMethodResponse(Long id, Method method, Long userId) {}
