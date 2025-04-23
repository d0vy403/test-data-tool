package lt.codeacademy.testdatatool.exception;

public class UserLoginNotFoundException extends RuntimeException {
  public UserLoginNotFoundException(Long id) {
    super("User login not found: " + id);
  }
}
