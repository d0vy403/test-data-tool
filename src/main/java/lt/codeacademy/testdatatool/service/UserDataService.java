package lt.codeacademy.testdatatool.service;

import jakarta.transaction.Transactional;
import java.util.List;
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
  public List<GetUserDataResponse> getUserData(
      String userName, Environment environment, Channel channel) {
    return userDataRepository.findAll().stream()
        .filter(user -> isMatchingUserName(user, userName))
        .filter(user -> isMatchingEnvironment(user, environment))
        .filter(user -> isMatchingChannel(user, channel))
        .map(userDataMapper::toGetUserDataResponse)
        .toList();
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

  private boolean isMatchingUserName(UserData user, String userName) {
    return userName == null || userName.isEmpty() || user.getUsername().equals(userName);
  }

  private boolean isMatchingEnvironment(UserData user, Environment environment) {
    return environment == null || user.getEnvironment().equals(environment);
  }

  private boolean isMatchingChannel(UserData user, Channel channel) {
    return channel == null || user.getChannel().equals(channel);
  }
}
