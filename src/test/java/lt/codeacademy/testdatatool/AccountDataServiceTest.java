package lt.codeacademy.testdatatool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lt.codeacademy.testdatatool.dto.GetAccountDataResponse;
import lt.codeacademy.testdatatool.entity.AccountData;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.mapper.AccountDataMapper;
import lt.codeacademy.testdatatool.repository.AccountDataRepository;
import lt.codeacademy.testdatatool.service.AccountDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AccountDataServiceTest {

  @Mock private AccountDataRepository accountDataRepository;

  @Mock private AccountDataMapper accountDataMapper;

  @InjectMocks private AccountDataService accountDataService;

  List<AccountData> accountDataList = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    UserData user1 = new UserData();
    user1.setId(1L);
    user1.setUsername("testUser1");

    UserData user2 = new UserData();
    user2.setId(2L);
    user2.setUsername("testUser2");

    UserData user3 = new UserData();
    user3.setId(3L);
    user3.setUsername("testUser3");

    AccountData account1 = new AccountData();
    account1.setId(1L);
    account1.setAccountNumber("LT234567890123456784");
    account1.setType("SAVINGS");
    account1.setBalance(new BigDecimal("1000.00"));
    account1.setCurrency("USD");
    account1.setBlocked(false);
    account1.setUserData(user1);

    AccountData account2 = new AccountData();
    account2.setId(2L);
    account2.setAccountNumber("LT234567890123456781");
    account2.setType("INVESTMENT");
    account2.setBalance(new BigDecimal("2000.00"));
    account2.setCurrency("USD");
    account2.setBlocked(false);
    account2.setUserData(user2);

    AccountData account3 = new AccountData();
    account3.setId(3L);
    account3.setAccountNumber("LT234567890123456785");
    account3.setType("SAVINGS");
    account3.setBalance(new BigDecimal("3000.00"));
    account3.setCurrency("EUR");
    account3.setBlocked(true);
    account3.setUserData(user3);

    accountDataList.add(account1);
    accountDataList.add(account2);
    accountDataList.add(account3);
  }

  @Test
  public void testGetAccountDataWithNoFilters() {

    when(accountDataRepository.findAll()).thenReturn(accountDataList);
    List<GetAccountDataResponse> expectedResponse =
        accountDataList.stream().map(accountDataMapper::toGetAccountDataResponse).toList();

    for (int i = 0; i < accountDataList.size(); i++) {
      when(accountDataMapper.toGetAccountDataResponse(accountDataList.get(i)))
          .thenReturn(expectedResponse.get(i));
    }

    List<GetAccountDataResponse> actualResponse =
        accountDataService.getAccountData(null, null, null);

    assertEquals(expectedResponse, actualResponse);
    System.out.println("testGetAccountDataWithNoFilters executed successfully.");
  }

  @Test
  public void testGetAccountDataWithCurrencyFilter() {
    when(accountDataRepository.findAll()).thenReturn(accountDataList);

    List<AccountData> filteredAccounts =
        accountDataList.stream().filter(account -> account.getCurrency().equals("USD")).toList();

    List<GetAccountDataResponse> expectedResponse = new ArrayList<>();

    for (AccountData account : filteredAccounts) {
      GetAccountDataResponse response =
          new GetAccountDataResponse(
              account.getId(),
              account.getAccountNumber(),
              account.getType(),
              account.getBalance(),
              account.getCurrency(),
              account.isBlocked(),
              account.getUserData().getId());
      expectedResponse.add(response);

      when(accountDataMapper.toGetAccountDataResponse(account)).thenReturn(response);
    }

    List<GetAccountDataResponse> actualResponse =
        accountDataService.getAccountData("USD", null, null);

    assertEquals(expectedResponse, actualResponse);
    System.out.println("testGetAccountDataWithCurrencyFilter executed successfully.");
  }
}
