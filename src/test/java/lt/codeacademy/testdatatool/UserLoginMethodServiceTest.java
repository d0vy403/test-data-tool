package lt.codeacademy.testdatatool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import lt.codeacademy.testdatatool.dto.GetUserLoginMethodResponse;
import lt.codeacademy.testdatatool.entity.Method;
import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.mapper.UserLoginMethodMapper;
import lt.codeacademy.testdatatool.repository.UserLoginMethodRepository;
import lt.codeacademy.testdatatool.service.UserLoginMethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserLoginMethodServiceTest {

  @Mock private UserLoginMethodRepository userLoginMethodRepository;

  @Mock private UserLoginMethodMapper userLoginMethodMapper;

  @InjectMocks private UserLoginMethodService userLoginMethodService;

  List<UserLoginMethod> userLoginMethodList = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    UserData user1 = new UserData();
    user1.setId(1L);
    user1.setUsername("testUser1");

    UserData user2 = new UserData();
    user2.setId(2L);
    user2.setUsername("testUser2");

    UserLoginMethod loginMethod1 = new UserLoginMethod();
    loginMethod1.setId(1L);
    loginMethod1.setMethod(Method.BIOMETRIC);
    loginMethod1.setUserData(user1);

    UserLoginMethod loginMethod2 = new UserLoginMethod();
    loginMethod2.setId(2L);
    loginMethod2.setMethod(Method.GENIUS_ID);
    loginMethod2.setUserData(user2);

    userLoginMethodList.add(loginMethod1);
    userLoginMethodList.add(loginMethod2);
  }

  @Test
  public void testGetUserLoginMethodWithNoFilters() {

    when(userLoginMethodRepository.findAll()).thenReturn(userLoginMethodList);
    List<GetUserLoginMethodResponse> expectedResponse =
        userLoginMethodList.stream()
            .map(
                loginMethod ->
                    new GetUserLoginMethodResponse(
                        loginMethod.getId(),
                        loginMethod.getMethod(),
                        loginMethod.getUserData().getId()))
            .toList();

    for (int i = 0; i < userLoginMethodList.size(); i++) {
      when(userLoginMethodMapper.toGetUserLoginMethodResponse(userLoginMethodList.get(i)))
          .thenReturn(expectedResponse.get(i));
    }

    List<GetUserLoginMethodResponse> actualResponse =
        userLoginMethodService.getUserLoginMethod(null, null);

    assertEquals(expectedResponse, actualResponse);
    System.out.println("testGetUserLoginMethodWithNoFilters executed successfully.");
  }

  @Test
  public void testGetUserLoginMethodWithMethodFilter() {

    when(userLoginMethodRepository.findAll()).thenReturn(userLoginMethodList);

    List<GetUserLoginMethodResponse> expectedResponse =
        userLoginMethodList.stream()
            .filter(loginMethod -> loginMethod.getMethod().equals(Method.GENIUS_ID))
            .map(
                loginMethod ->
                    new GetUserLoginMethodResponse(
                        loginMethod.getId(),
                        loginMethod.getMethod(),
                        loginMethod.getUserData().getId()))
            .toList();

    when(userLoginMethodMapper.toGetUserLoginMethodResponse(userLoginMethodList.get(1)))
        .thenReturn(
            new GetUserLoginMethodResponse(
                2L,
                userLoginMethodList.get(1).getMethod(),
                userLoginMethodList.get(1).getUserData().getId()));

    List<GetUserLoginMethodResponse> actualResponse =
        userLoginMethodService.getUserLoginMethod(Method.GENIUS_ID, null);

    assertEquals(expectedResponse, actualResponse);
    System.out.println("testGetUserLoginMethodWithMethodFilter executed successfully.");
  }
}
