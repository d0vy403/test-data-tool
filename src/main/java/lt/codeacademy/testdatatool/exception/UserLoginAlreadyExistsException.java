package lt.codeacademy.testdatatool.exception;

public class UserLoginAlreadyExistsException extends RuntimeException {
  public UserLoginAlreadyExistsException(Long userId) {
      super("User: " + userId + " already has this login method");
  }
}
