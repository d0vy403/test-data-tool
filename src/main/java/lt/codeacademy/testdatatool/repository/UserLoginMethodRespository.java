package lt.codeacademy.testdatatool.repository;

import lt.codeacademy.testdatatool.entity.Method;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginMethodRespository extends JpaRepository<UserLoginMethod, Long> {
    boolean existsByUserDataAndMethod(UserData userData, Method method);
}
