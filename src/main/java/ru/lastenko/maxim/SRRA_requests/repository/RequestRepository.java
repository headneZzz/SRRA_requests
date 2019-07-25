package ru.lastenko.maxim.SRRA_requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.maxim.SRRA_requests.entity.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByOrderByIdDesc();

}