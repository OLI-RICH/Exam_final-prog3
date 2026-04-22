package com.exam.project.service;

import com.exam.project.repository.CollectivityRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class FederationService {

    private final CollectivityRepository collectivityRepository;

    public FederationService(CollectivityRepository collectivityRepository) {
        this.collectivityRepository = collectivityRepository;
    }

    public void assignCollectivityIdentity(String id, String number, String name) throws SQLException {
        if (collectivityRepository.isIdentityAlreadySet(id)) {
            throw new IllegalStateException("Identity is already fixed and cannot be modified.");
        }
        if (collectivityRepository.uniqueNameExists(name)) {
            throw new IllegalArgumentException("The name '" + name + "' is already taken.");
        }
        collectivityRepository.updateIdentity(id, number, name);
    }
}