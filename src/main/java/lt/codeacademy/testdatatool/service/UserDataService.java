package lt.codeacademy.testdatatool.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.entity.*;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {
  private final UserDataRepository userDataRepository;

  public UserData addUserData(UserData userData) {
    return userDataRepository.saveAndFlush(userData);
  }

  @Transactional
  public List<UserData> getUserData() {
    return userDataRepository.findAll();
  }

  public UserData getUserDataById(Long id) {
    return userDataRepository
        .findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("User data with id " + id + " not found"));
  }

  public UserData updateUserData(UserData userData) {
    return userDataRepository.saveAndFlush(userData);
  }

  public void deleteUserData(Long id) {
    userDataRepository.deleteById(id);
  }
}
