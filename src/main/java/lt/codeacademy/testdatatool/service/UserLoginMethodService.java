package lt.codeacademy.testdatatool.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateUserLoginMethodRequest;
import lt.codeacademy.testdatatool.dto.GetUserLoginMethodResponse;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import lt.codeacademy.testdatatool.mapper.UserLoginMethodMapper;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import lt.codeacademy.testdatatool.repository.UserLoginMethodRespository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginMethodService {
  private final UserLoginMethodRespository userLoginMethodRespository;
  private final UserLoginMethodMapper userLoginMethodMapper;
  private final UserDataRepository userDataRepository;

  public GetUserLoginMethodResponse addUserLoginMethod(CreateUserLoginMethodRequest request) {
    UserData userData =
        userDataRepository
            .findById(request.userId())
            .orElseThrow(() -> new EntityNotFoundException("User data not found"));
    UserLoginMethod userLoginMethod = userLoginMethodMapper.toUserLoginMethod(request);
    userLoginMethod.setUserData(userData);
    userLoginMethodRespository.save(userLoginMethod);
    return userLoginMethodMapper.toGetUserLoginMethodResponse(userLoginMethod);
  }

  public List<GetUserLoginMethodResponse> getUserLoginMethod() {
    List<UserLoginMethod> userLoginMethods = userLoginMethodRespository.findAll();
    return userLoginMethods.stream()
        .map(userLoginMethodMapper::toGetUserLoginMethodResponse)
        .toList();
  }

  public GetUserLoginMethodResponse getUserLoginMethodById(Long id) {
    UserLoginMethod userLoginMethod =
        userLoginMethodRespository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("User login method with id " + id + " not found"));
    return userLoginMethodMapper.toGetUserLoginMethodResponse(userLoginMethod);
  }

  @Transactional
  public void deleteUserLoginMethod(Long id) {
    UserLoginMethod userLoginMethod =
        userLoginMethodRespository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("User login method with id " + id + " not found"));
    UserData user = userLoginMethod.getUserData();
    user.getUserLoginMethod().remove(userLoginMethod);
  }
}
