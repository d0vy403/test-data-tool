package lt.codeacademy.testdatatool.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateAccountDataRequest;
import lt.codeacademy.testdatatool.dto.GetAccountDataResponse;
import lt.codeacademy.testdatatool.dto.UpdateAccountDataRequest;
import lt.codeacademy.testdatatool.entity.AccountData;
import lt.codeacademy.testdatatool.entity.UserData;
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
            .orElseThrow(() -> new EntityNotFoundException("User data not found"));
    AccountData accountData = accountDataMapper.toAccountData(request);
    accountData.setUserData(userData);
    AccountData savedAccountData = accountDataRepository.saveAndFlush(accountData);
    return accountDataMapper.toGetAccountDataResponse(savedAccountData);
  }

  public List<GetAccountDataResponse> getAccountData() {
    List<AccountData> accountDataList = accountDataRepository.findAll();
    return accountDataList.stream().map(accountDataMapper::toGetAccountDataResponse).toList();
  }

  public GetAccountDataResponse getAccountDataById(Long id) {
    AccountData accountData =
        accountDataRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Account data with id " + id + " not found"));
    return accountDataMapper.toGetAccountDataResponse(accountData);
  }

  public GetAccountDataResponse updateAccountData(Long id, UpdateAccountDataRequest request) {
    AccountData accountData =
        accountDataRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Account data with id " + id + " not found"));
    accountDataMapper.updateAccountData(request, accountData);
    AccountData updatedAccountData = accountDataRepository.saveAndFlush(accountData);
    return accountDataMapper.toGetAccountDataResponse(updatedAccountData);
  }

  @Transactional
  public void deleteAccountData(Long id) {
    AccountData accountData =
        accountDataRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Account data with id " + id + " not found"));
    UserData user = accountData.getUserData();
    user.getAccount().remove(accountData);
  }
}
