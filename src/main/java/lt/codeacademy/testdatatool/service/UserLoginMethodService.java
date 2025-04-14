package lt.codeacademy.testdatatool.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import lt.codeacademy.testdatatool.repository.UserLoginMethodRespository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginMethodService {
  private final UserLoginMethodRespository userLoginMethodRespository;

  public UserLoginMethod addUserLoginMethod(UserLoginMethod userLoginMethod) {
    return userLoginMethodRespository.saveAndFlush(userLoginMethod);
  }

  public List<UserLoginMethod> getUserLoginMethod() {
    return userLoginMethodRespository.findAll();
  }

  public UserLoginMethod getUserLoginMethodById(Long id) {
    return userLoginMethodRespository
        .findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("User login method with id " + id + " not found"));
  }
}
