package lt.codeacademy.testdatatool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateUserDataRequest;
import lt.codeacademy.testdatatool.dto.GetUserDataResponse;
import lt.codeacademy.testdatatool.dto.UpdateUserDataRequest;
import lt.codeacademy.testdatatool.entity.*;
import lt.codeacademy.testdatatool.exception.InvalidDataException;
import lt.codeacademy.testdatatool.exception.UserAlreadyExistsException;
import lt.codeacademy.testdatatool.exception.UserNotFoundException;
import lt.codeacademy.testdatatool.mapper.UserDataMapper;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {
  private final UserDataRepository userDataRepository;
  private final UserDataMapper userDataMapper;

  public GetUserDataResponse addUserData(CreateUserDataRequest request) {
    if (userDataRepository.existsByUsername(request.userName())) {
      throw new UserAlreadyExistsException(request.userName());
    }
    try {
      UserData userData = userDataMapper.toUserData(request);
      UserData savedUserData = userDataRepository.save(userData);
      return userDataMapper.toGetUserDataResponse(savedUserData);
    } catch (Exception e) {
      throw new InvalidDataException("Failed to add user data");
    }
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
        userDataRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    return userDataMapper.toGetUserDataResponse(userData);
  }

  @Transactional
  public GetUserDataResponse updateUserData(Long id, UpdateUserDataRequest request) {
    UserData userData =
        userDataRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    userDataMapper.updateUserData(request, userData);
    try {
      UserData updatedUserData = userDataRepository.saveAndFlush(userData);
      return userDataMapper.toGetUserDataResponse(updatedUserData);
    } catch (Exception e) {
      throw new InvalidDataException("Failed to update user data");
    }
  }

  @Transactional
  public void deleteUserData(Long id) {
    if (!userDataRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }
    userDataRepository.deleteById(id);
  }
}
