package ru.lastenko.maxim.SRRA_requests.service;

import org.springframework.stereotype.Service;
import ru.lastenko.maxim.SRRA_requests.entity.requests.Executor;
import ru.lastenko.maxim.SRRA_requests.repository.requests.ExecutorRepository;


import java.util.List;

@Service
public class ExecutorService {

    private final ExecutorRepository repository;

    public ExecutorService(ExecutorRepository repository) {
        this.repository = repository;
    }

    public Executor save(Executor executor) {
        return repository.save(executor);
    }

    public Executor getById(int Id) {
        return repository.findById(Id).orElse(null);
    }

    public List<Executor> getAll() {
        return repository.findAllByOrderByNameAsc();
    }

}
