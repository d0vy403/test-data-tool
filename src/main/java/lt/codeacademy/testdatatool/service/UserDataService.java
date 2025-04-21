package lt.codeacademy.testdatatool.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateUserDataRequest;
import lt.codeacademy.testdatatool.dto.GetUserDataResponse;
import lt.codeacademy.testdatatool.dto.UpdateUserDataRequest;
import lt.codeacademy.testdatatool.entity.*;
import lt.codeacademy.testdatatool.mapper.UserDataMapper;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {
  private final UserDataRepository userDataRepository;
  private final UserDataMapper userDataMapper;

  public GetUserDataResponse addUserData(CreateUserDataRequest request) {
    UserData userData = userDataMapper.toUserData(request);
    UserData savedUserData = userDataRepository.save(userData);
    return userDataMapper.toGetUserDataResponse(savedUserData);
  }

  @Transactional
  public List<GetUserDataResponse> getUserData() {
    List<UserData> userDataList = userDataRepository.findAll();
    return userDataList.stream()
        .map(userDataMapper::toGetUserDataResponse)
        .collect(Collectors.toList());
  }

  public GetUserDataResponse getUserDataById(Long id) {
    UserData userData =
        userDataRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("User data with id " + id + " not found"));
    return userDataMapper.toGetUserDataResponse(userData);
  }

  @Transactional
  public GetUserDataResponse updateUserData(Long id, UpdateUserDataRequest request) {
    UserData userData =
        userDataRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("User data with id " + id + " not found"));
    userDataMapper.updateUserData(request, userData);
    UserData updatedUserData = userDataRepository.saveAndFlush(userData);
    return userDataMapper.toGetUserDataResponse(updatedUserData);
  }

  @Transactional
  public void deleteUserData(Long id) {
    if (!userDataRepository.existsById(id)) {
      throw new EntityNotFoundException("User data with id " + id + " not found");
    }
    userDataRepository.deleteById(id);
  }
}
