package lt.codeacademy.testdatatool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "user_data")
@Getter
@Setter
@ToString(exclude = {"account", "userLoginMethod"})
public class UserData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;
  @Column(nullable = false, length = 50)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Channel channel;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Environment environment;

  @OneToMany(mappedBy = "userData", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<AccountData> account;

  @OneToMany(mappedBy = "userData", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<UserLoginMethod> userLoginMethod;
}
