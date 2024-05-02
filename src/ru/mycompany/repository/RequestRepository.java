package ru.mycompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mycompany.model.entity.Request;
import ru.mycompany.model.entity.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByStatus(RequestStatus requestStatus);
}
