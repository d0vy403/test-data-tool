package lt.codeacademy.testdatatool.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateAccountDataRequest;
import lt.codeacademy.testdatatool.dto.GetAccountDataResponse;
import lt.codeacademy.testdatatool.dto.UpdateAccountDataRequest;
import lt.codeacademy.testdatatool.entity.AccountData;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.exception.AccountAlreadyExistsException;
import lt.codeacademy.testdatatool.exception.AccountNotFoundException;
import lt.codeacademy.testdatatool.exception.InvalidDataException;
import lt.codeacademy.testdatatool.exception.UserNotFoundException;
import lt.codeacademy.testdatatool.mapper.AccountDataMapper;
import lt.codeacademy.testdatatool.repository.AccountDataRepository;
import lt.codeacademy.testdatatool.repository.UserDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDataService {

  private final AccountDataRepository accountDataRepository;
  private final UserDataRepository userDataRepository;
  private final AccountDataMapper accountDataMapper;

  public GetAccountDataResponse addAccountData(CreateAccountDataRequest request) {
    UserData userData =
        userDataRepository
            .findById(request.userId())
            .orElseThrow(() -> new UserNotFoundException(request.userId()));
    if (accountDataRepository.existsByAccountNumber(request.accountNumber())) {
      throw new AccountAlreadyExistsException(request.accountNumber());
    }
    try {
      AccountData accountData = accountDataMapper.toAccountData(request);
      accountData.setUserData(userData);
      AccountData savedAccountData = accountDataRepository.saveAndFlush(accountData);
      return accountDataMapper.toGetAccountDataResponse(savedAccountData);
    } catch (Exception e) {
      throw new InvalidDataException("Failed to add account data");
    }
  }

  public List<GetAccountDataResponse> getAccountData(String currency, String type, Long userId) {
    return accountDataRepository.findAll().stream()
        .filter(account -> isMatchingCurrency(account, currency))
        .filter(account -> isMatchingType(account, type))
        .filter(account -> isMatchingUser(account, userId))
        .map(accountDataMapper::toGetAccountDataResponse)
        .toList();
  }

  public GetAccountDataResponse getAccountDataById(Long id) {
    AccountData accountData =
        accountDataRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    return accountDataMapper.toGetAccountDataResponse(accountData);
  }

  public GetAccountDataResponse updateAccountData(Long id, UpdateAccountDataRequest request) {
    AccountData accountData =
        accountDataRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    accountDataMapper.updateAccountData(request, accountData);
    try {
      AccountData updatedAccountData = accountDataRepository.saveAndFlush(accountData);
      return accountDataMapper.toGetAccountDataResponse(updatedAccountData);
    } catch (Exception e) {
      throw new InvalidDataException("Failed to update account data");
    }
  }

  @Transactional
  public void deleteAccountData(Long id) {
    AccountData accountData =
        accountDataRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    UserData user = accountData.getUserData();
    user.getAccount().remove(accountData);
  }

  private boolean isMatchingCurrency(AccountData account, String currency) {
    return currency == null || currency.isEmpty() || account.getCurrency().equals(currency);
  }

  private boolean isMatchingType(AccountData account, String type) {
    return type == null || type.isEmpty() || account.getType().equals(type);
  }

  private boolean isMatchingUser(AccountData account, Long userId) {
    return userId == null || account.getUserData().getId().equals(userId);
  }
}
