package lt.codeacademy.testdatatool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "account_data")
@Getter
@Setter
@ToString(exclude = "userData")
public class AccountData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true)
  private Long id;

  @Column(nullable = false, unique = true, length = 50, name = "account_number")
  private String accountNumber;
  @Column(nullable = false, length = 50)
  private String type;
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal balance;
  @Column(nullable = false, length = 10)
  private String currency;
  @Column(nullable = false)
  private boolean blocked;

  @ManyToOne(optional = false)
  private UserData userData;
}
