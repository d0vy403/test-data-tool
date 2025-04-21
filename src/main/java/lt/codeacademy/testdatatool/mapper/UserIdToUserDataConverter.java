package lt.codeacademy.testdatatool.mapper;

import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserIdToUserDataConverter {

  private final UserDataRepository userDataRepository;

  @Named("convertToUserData")
  public UserData convertToUserData(Long userId) {
    return userDataRepository.findById(userId).orElse(null);
  }
}
