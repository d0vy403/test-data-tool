package lt.codeacademy.testdatatool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateUserLoginMethodRequest;
import lt.codeacademy.testdatatool.dto.GetUserLoginMethodResponse;
import lt.codeacademy.testdatatool.entity.Method;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import lt.codeacademy.testdatatool.exception.InvalidDataException;
import lt.codeacademy.testdatatool.exception.UserLoginAlreadyExistsException;
import lt.codeacademy.testdatatool.exception.UserLoginNotFoundException;
import lt.codeacademy.testdatatool.exception.UserNotFoundException;
import lt.codeacademy.testdatatool.mapper.UserLoginMethodMapper;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import lt.codeacademy.testdatatool.repository.UserLoginMethodRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginMethodService {
  private final UserLoginMethodRepository userLoginMethodRepository;
  private final UserLoginMethodMapper userLoginMethodMapper;
  private final UserDataRepository userDataRepository;

  public GetUserLoginMethodResponse addUserLoginMethod(CreateUserLoginMethodRequest request) {
    UserData userData =
        userDataRepository
            .findById(request.userId())
            .orElseThrow(() -> new UserNotFoundException(request.userId()));
    if (userLoginMethodRepository.existsByUserDataAndMethod(userData, request.method())) {
      throw new UserLoginAlreadyExistsException(request.userId());
    }

    try {
      UserLoginMethod userLoginMethod = userLoginMethodMapper.toUserLoginMethod(request);
      userLoginMethod.setUserData(userData);
      userLoginMethodRepository.save(userLoginMethod);
      return userLoginMethodMapper.toGetUserLoginMethodResponse(userLoginMethod);
    } catch (Exception e) {
      throw new InvalidDataException("Failed to add user login method");
    }
  }

  public List<GetUserLoginMethodResponse> getUserLoginMethod(Method method, Long userId) {
    return userLoginMethodRepository.findAll().stream()
        .filter(loginMethod -> isMatchingMethod(loginMethod, method))
        .filter(loginMethod -> isMatchingUserId(loginMethod, userId))
        .map(userLoginMethodMapper::toGetUserLoginMethodResponse)
        .toList();
  }

  public GetUserLoginMethodResponse getUserLoginMethodById(Long id) {
    UserLoginMethod userLoginMethod =
        userLoginMethodRepository
            .findById(id)
            .orElseThrow(() -> new UserLoginNotFoundException(id));
    return userLoginMethodMapper.toGetUserLoginMethodResponse(userLoginMethod);
  }

  @Transactional
  public void deleteUserLoginMethod(Long id) {
    UserLoginMethod userLoginMethod =
        userLoginMethodRepository
            .findById(id)
            .orElseThrow(() -> new UserLoginNotFoundException(id));
    UserData user = userLoginMethod.getUserData();
    user.getUserLoginMethod().remove(userLoginMethod);
  }

  private boolean isMatchingMethod(UserLoginMethod loginMethod, Method method) {
    return method == null || loginMethod.getMethod().equals(method);
  }

  private boolean isMatchingUserId(UserLoginMethod loginMethod, Long userId) {
    return userId == null || loginMethod.getUserData().getId().equals(userId);
  }
}
