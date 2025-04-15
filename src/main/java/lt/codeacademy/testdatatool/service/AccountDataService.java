package lt.codeacademy.testdatatool.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.entity.AccountData;
import lt.codeacademy.testdatatool.repository.AccountDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDataService {

  private final AccountDataRepository accountDataRepository;

  public AccountData addAccountData(AccountData accountData) {
    return accountDataRepository.saveAndFlush(accountData);
  }

  public List<AccountData> getAccountData() {
    return accountDataRepository.findAll();
  }

  public AccountData getAccountDataById(Long id) {
    return accountDataRepository
        .findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("Account data with id " + id + " not found"));
  }

  public AccountData updateAccountData(AccountData accountData) {
    return accountDataRepository.saveAndFlush(accountData);
  }

  public void deleteAccountData(Long id) {
    accountDataRepository.deleteById(id);
  }
}
