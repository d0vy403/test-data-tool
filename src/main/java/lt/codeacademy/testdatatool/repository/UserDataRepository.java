package lt.codeacademy.testdatatool.repository;

import lt.codeacademy.testdatatool.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    boolean existsByUsername(String username);
}
