package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.installation.HozPitInstallation;
import org.simpel.pumpingUnits.model.installation.PNSInstallationAFEIJP;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PNSServiceAFEIJP implements InstallationServiceInterface <PNSInstallationAFEIJP> {
    @Override
    public PNSInstallationAFEIJP save(InstallationRequest parentInstallations) {
        return null;
    }

    @Override
    public List<PNSInstallationAFEIJP> getAll(InstallationRequest parentInstallations) {
        return List.of();
    }
}
