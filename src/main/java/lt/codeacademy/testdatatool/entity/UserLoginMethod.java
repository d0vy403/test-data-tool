package lt.codeacademy.testdatatool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_login_method")
@Getter
@Setter
@ToString(exclude = "userData")
public class UserLoginMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Method method;

  @ManyToOne(optional = false)
  private UserData userData;
}
