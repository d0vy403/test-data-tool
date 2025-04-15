package lt.codeacademy.testdatatool.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import lt.codeacademy.testdatatool.service.UserLoginMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/user-login-method")
@RequiredArgsConstructor
public class UserLoginMethodController {
  private final UserLoginMethodService userLoginMethodService;

  @PostMapping
  public ResponseEntity<UserLoginMethod> addUserLoginMethod(
      @RequestBody UserLoginMethod userLoginMethod) {
    UserLoginMethod newUserLoginMethod = userLoginMethodService.addUserLoginMethod(userLoginMethod);
    return new ResponseEntity<>(newUserLoginMethod, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<UserLoginMethod>> getUserLoginMethods() {
    return new ResponseEntity<>(userLoginMethodService.getUserLoginMethod(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserLoginMethod> getUserLoginMethodById(@PathVariable Long id) {
    try {
      UserLoginMethod userLoginMethod = userLoginMethodService.getUserLoginMethodById(id);
      return new ResponseEntity<>(userLoginMethod, HttpStatus.OK);
    } catch (HttpClientErrorException.NotFound e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public void deleteUserLoginMethod(@PathVariable Long id) {
    userLoginMethodService.deleteUserLoginMethod(id);
  }
}
