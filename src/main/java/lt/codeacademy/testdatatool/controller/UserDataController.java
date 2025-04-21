package lt.codeacademy.testdatatool.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateUserDataRequest;
import lt.codeacademy.testdatatool.dto.GetUserDataResponse;
import lt.codeacademy.testdatatool.dto.UpdateUserDataRequest;
import lt.codeacademy.testdatatool.service.UserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-data")
@RequiredArgsConstructor
public class UserDataController {
  private final UserDataService userDataService;

  @PostMapping
  public ResponseEntity<GetUserDataResponse> addUserData(
      @RequestBody CreateUserDataRequest request) {
    GetUserDataResponse newUserData = userDataService.addUserData(request);
    return new ResponseEntity<>(newUserData, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<GetUserDataResponse>> getUserData() {
    List<GetUserDataResponse> userDataList = userDataService.getUserData();
    return new ResponseEntity<>(userDataList, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetUserDataResponse> getUserDataById(@PathVariable Long id) {
    return new ResponseEntity<>(userDataService.getUserDataById(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GetUserDataResponse> updateUserData(
      @PathVariable Long id, @RequestBody UpdateUserDataRequest request) {
    return new ResponseEntity<>(userDataService.updateUserData(id, request), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserData(@PathVariable Long id) {
    userDataService.deleteUserData(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
