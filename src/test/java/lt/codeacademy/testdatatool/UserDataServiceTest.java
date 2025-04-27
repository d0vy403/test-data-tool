package lt.codeacademy.testdatatool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import lt.codeacademy.testdatatool.dto.GetUserDataResponse;
import lt.codeacademy.testdatatool.entity.Channel;
import lt.codeacademy.testdatatool.entity.Environment;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.mapper.UserDataMapper;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import lt.codeacademy.testdatatool.service.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserDataServiceTest {

  @Mock private UserDataRepository userDataRepository;

  @Mock private UserDataMapper userDataMapper;

  @InjectMocks private UserDataService userDataService;

  List<UserData> userDataList = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    UserData user1 = new UserData();
    user1.setId(1L);
    user1.setUsername("testUser1");
    user1.setEnvironment(Environment.PRODUCTION);
    user1.setChannel(Channel.CORPORATE);

    UserData user2 = new UserData();
    user2.setId(2L);
    user2.setUsername("testUser2");
    user2.setEnvironment(Environment.TEST);
    user2.setChannel(Channel.CORPORATE);

    UserData user3 = new UserData();
    user3.setId(3L);
    user3.setUsername("testUser1");
    user3.setEnvironment(Environment.DEVELOPMENT);
    user3.setChannel(Channel.PERSONAL);

    userDataList.add(user1);
    userDataList.add(user2);
    userDataList.add(user3);
  }

  @Test
  public void testGetUserDataWithNoFilters() {

    when(userDataRepository.findAll()).thenReturn(userDataList);
    List<GetUserDataResponse> expectedResponse =
        userDataList.stream().map(userDataMapper::toGetUserDataResponse).toList();
    for (int i = 0; i < userDataList.size(); i++) {
      when(userDataMapper.toGetUserDataResponse(userDataList.get(i)))
          .thenReturn(expectedResponse.get(i));
    }

    List<GetUserDataResponse> actualResponse = userDataService.getUserData(null, null, null);

    assertEquals(expectedResponse, actualResponse);
    System.out.println("testGetUserDataWithNoFilters executed successfully.");
  }

  @Test
  public void testGetUserDataWithUserNameFilter() {
    when(userDataRepository.findAll()).thenReturn(userDataList);

    List<GetUserDataResponse> expectedResponse =
        userDataList.stream()
            .filter(user -> user.getUsername().equals("testUser1"))
            .map(userDataMapper::toGetUserDataResponse)
            .toList();

    when(userDataMapper.toGetUserDataResponse(userDataList.get(0)))
        .thenReturn(
            new GetUserDataResponse(
                1L,
                "testUser1",
                "password1",
                Environment.PRODUCTION,
                Channel.CORPORATE,
                new ArrayList<>(),
                new ArrayList<>()));
    when(userDataMapper.toGetUserDataResponse(userDataList.get(2)))
        .thenReturn(
            new GetUserDataResponse(
                3L,
                "testUser1",
                "password3",
                Environment.DEVELOPMENT,
                Channel.PERSONAL,
                new ArrayList<>(),
                new ArrayList<>()));

    List<GetUserDataResponse> actualResponse = userDataService.getUserData("testUser1", null, null);

    assertEquals(expectedResponse, actualResponse);
    System.out.println("testGetUserDataWithUserNameFilter executed successfully.");
  }
}
