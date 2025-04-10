package lt.codeacademy.testdatatool.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        .orElseThrow(() -> new RuntimeException("Account data not found"));
  }

  public void addTestUserData() {
    List<UserData> userDataList = new ArrayList<>();

    UserData userData1 = new UserData();
    userData1.setUsername("user1");
    userData1.setPassword("password1");
    userData1.setChannel(Channel.PERSONAL);
    userData1.setEnvironment(Environment.DEVELOPMENT);

    AccountData accountData1 = new AccountData();
    accountData1.setAccountNumber("LT121000011101001000");
    accountData1.setType("SAVINGS");
    accountData1.setCurrency("EUR");
    accountData1.setBalance(new BigDecimal("2000.546"));
    accountData1.setBlocked(false);
    accountData1.setUserData(userData1);

    UserLoginMethod userLoginMethod1 = new UserLoginMethod();
    userLoginMethod1.setMethod(Method.BIOMETRIC);
    userLoginMethod1.setUserData(userData1);

    userData1.setAccount(List.of(accountData1));
    userData1.setUserLoginMethod(List.of(userLoginMethod1));

    userDataList.add(userData1);
    userDataRepository.saveAllAndFlush(userDataList);
  }
}
