package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PNSServiceERW implements InstallationServiceInterface<PNSInstallationERW> {
    @Override
    public PNSInstallationERW save(InstallationRequest parentInstallations) {
        return null;
    }

    @Override
    public List<PNSInstallationERW> getAll(InstallationRequest parentInstallations) {
        return List.of();
    }
}
