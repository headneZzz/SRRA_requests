package ru.gosarhro.SRRA_requests.service;

import org.springframework.stereotype.Service;
import ru.gosarhro.SRRA_requests.entity.personal_data.ComputerWithAccess;
import ru.gosarhro.SRRA_requests.repository.personal_data.ComputerWithAccessRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComputerWithAccessService {
    private final ComputerWithAccessRepository repository;

    public List<String> getWhitelistOfIps() {
        return repository.findAll().stream()
                .map(ComputerWithAccess::getIp)
                .collect(Collectors.toList());
    }

    public ComputerWithAccessService(ComputerWithAccessRepository repository) {
        this.repository = repository;
    }

    public ComputerWithAccess getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<ComputerWithAccess> getAll() {
        return repository.findAll();
    }
}
