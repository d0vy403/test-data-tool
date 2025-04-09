package lt.codeacademy.testdatatool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class AccountData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String accountNumber;
  private String type;
  private double balance;
  private String currency;
  private boolean blocked;

  @ManyToOne private UserData userData;
}
