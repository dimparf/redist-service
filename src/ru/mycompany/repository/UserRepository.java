package ru.mycompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mycompany.model.entity.User;
import ru.mycompany.model.entity.UserStatus;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByStatus(UserStatus userStatus);
}
