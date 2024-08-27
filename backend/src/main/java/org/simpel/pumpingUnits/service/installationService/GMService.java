package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.repository.GMRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GMService implements InstallationServiceInterface<GMInstallation> {

    private final GMRepository repository;

    public GMService(GMRepository repository) {
        this.repository = repository;
    }

    @Override
    public GMInstallation save(InstallationRequest parentInstallations) {
       GMInstallation installation = new GMInstallation();
        return repository.save(installation);
    }

    @Override
    public List<GMInstallation> getAll(InstallationRequest parentInstallations) {
        return repository.findAll();
    }


}
