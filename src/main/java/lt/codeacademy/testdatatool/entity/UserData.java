package lt.codeacademy.testdatatool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class UserData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;

  @Enumerated(EnumType.STRING)
  private Channel channel;

  @Enumerated(EnumType.STRING)
  private Environment environment;

  @OneToMany(mappedBy = "userData", cascade = CascadeType.PERSIST)
  private List<AccountData> account;

  @OneToMany(mappedBy = "userData", cascade = CascadeType.PERSIST)
  private List<UserLoginMethod> userLoginMethod;
}
