package lt.codeacademy.testdatatool.repository;

import lt.codeacademy.testdatatool.entity.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDataRepository extends JpaRepository<AccountData, Long> {
    boolean existsByAccountNumber(String accountNumber);
}
