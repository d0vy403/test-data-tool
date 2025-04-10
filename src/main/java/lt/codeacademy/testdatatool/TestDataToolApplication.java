package lt.codeacademy.testdatatool;

import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.service.UserDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class TestDataToolApplication {
  private final UserDataService userDataService;

  public TestDataToolApplication(UserDataService userDataService) {
    this.userDataService = userDataService;
  }

  public static void main(String[] args) {
    SpringApplication.run(TestDataToolApplication.class, args);
  }
  
  @EventListener(ApplicationReadyEvent.class)
  public void insertData() {
    userDataService.addTestUserData();
    List<UserData> users = userDataService.getUserData();
    for (UserData user : users) {
      System.out.println("User: " + user); // Prints UserData (excluding collections)
      System.out.println("  Accounts: " + user.getAccount());
      System.out.println("  Login Methods: " + user.getUserLoginMethod());
    }
  }

}
