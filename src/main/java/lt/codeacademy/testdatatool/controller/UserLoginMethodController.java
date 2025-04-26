package lt.codeacademy.testdatatool.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.dto.CreateUserLoginMethodRequest;
import lt.codeacademy.testdatatool.dto.GetUserLoginMethodResponse;
import lt.codeacademy.testdatatool.entity.Method;
import lt.codeacademy.testdatatool.service.UserLoginMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-login-method")
@RequiredArgsConstructor
public class UserLoginMethodController {
  private final UserLoginMethodService userLoginMethodService;

  @PostMapping
  public ResponseEntity<GetUserLoginMethodResponse> addUserLoginMethod(
      @RequestBody CreateUserLoginMethodRequest request) {
    GetUserLoginMethodResponse newUserLoginMethod =
        userLoginMethodService.addUserLoginMethod(request);
    return new ResponseEntity<>(newUserLoginMethod, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<GetUserLoginMethodResponse>> getUserLoginMethods(
      @RequestParam(name = "method", required = false) Method method,
      @RequestParam(name = "userid", required = false) Long userId) {
    List<GetUserLoginMethodResponse> userLoginMethodResponseList =
        userLoginMethodService.getUserLoginMethod(method, userId);
    return new ResponseEntity<>(userLoginMethodResponseList, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetUserLoginMethodResponse> getUserLoginMethodById(@PathVariable Long id) {
    return new ResponseEntity<>(userLoginMethodService.getUserLoginMethodById(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserLoginMethod(@PathVariable Long id) {
    userLoginMethodService.deleteUserLoginMethod(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
