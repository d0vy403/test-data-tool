package lt.codeacademy.testdatatool.repository;

import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginMethodRespository extends JpaRepository<UserLoginMethod, Long> {}
