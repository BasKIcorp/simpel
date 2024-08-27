package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.installation.HozPitInstallation;
import org.simpel.pumpingUnits.repository.HozPitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HozPitService implements InstallationServiceInterface <HozPitInstallation>{

    private final HozPitRepository repository;

    public HozPitService(HozPitRepository repository) {
        this.repository = repository;
    }

    @Override
    public HozPitInstallation save(InstallationRequest  parentInstallations) {
        return null;
    }

    @Override
    public List<HozPitInstallation> getAll(InstallationRequest parentInstallations) {
        return List.of();
    }
}
