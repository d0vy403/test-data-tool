package lt.codeacademy.testdatatool.exception;

public class AccountAlreadyExistsException extends RuntimeException {
  public AccountAlreadyExistsException(String accountNumber) {
    super("Account: " + accountNumber + " already exists");
  }
}
