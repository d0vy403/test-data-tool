package lt.codeacademy.testdatatool.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.entity.AccountData;
import lt.codeacademy.testdatatool.service.AccountDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("api/account-data")
@RequiredArgsConstructor
public class AccountDataController {
  private final AccountDataService accountDataService;

  @PostMapping
  public ResponseEntity<AccountData> addAccountData(@RequestBody AccountData accountData) {
    AccountData newAccountData = accountDataService.addAccountData(accountData);
    return new ResponseEntity<>(newAccountData, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<AccountData>> getAccountData() {
    return new ResponseEntity<>(accountDataService.getAccountData(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountData> getAccountDataById(@PathVariable Long id) {
    try {
      AccountData accountData = accountDataService.getAccountDataById(id);
      return new ResponseEntity<>(accountData, HttpStatus.OK);
    } catch (HttpClientErrorException.NotFound e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<AccountData> updateAccountData(
      @PathVariable Long id, @RequestBody AccountData accountData) {
    try {
      accountData.setId(id);
      AccountData updatedAccountData = accountDataService.updateAccountData(accountData);
      return new ResponseEntity<>(updatedAccountData, HttpStatus.OK);
    } catch (HttpClientErrorException.NotFound e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AccountData> deleteAccountData(@PathVariable Long id) {
    accountDataService.deleteAccountData(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
