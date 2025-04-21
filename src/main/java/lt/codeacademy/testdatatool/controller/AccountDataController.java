package lt.codeacademy.testdatatool.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateAccountDataRequest;
import lt.codeacademy.testdatatool.dto.GetAccountDataResponse;
import lt.codeacademy.testdatatool.dto.UpdateAccountDataRequest;
import lt.codeacademy.testdatatool.service.AccountDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account-data")
@RequiredArgsConstructor
public class AccountDataController {
  private final AccountDataService accountDataService;

  @PostMapping
  public ResponseEntity<GetAccountDataResponse> addAccountData(
      @RequestBody CreateAccountDataRequest request) {
    GetAccountDataResponse newAccountData = accountDataService.addAccountData(request);
    return new ResponseEntity<>(newAccountData, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<GetAccountDataResponse>> getAccountData() {
    List<GetAccountDataResponse> accountData = accountDataService.getAccountData();
    return new ResponseEntity<>(accountData, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetAccountDataResponse> getAccountDataById(@PathVariable Long id) {
    return new ResponseEntity<>(accountDataService.getAccountDataById(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetAccountDataResponse> updateAccountData(
      @PathVariable Long id, @RequestBody UpdateAccountDataRequest request) {
    return new ResponseEntity<>(accountDataService.updateAccountData(id, request), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAccountData(@PathVariable Long id) {
    accountDataService.deleteAccountData(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
