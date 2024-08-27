package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class InstallationService {

    private final InstallationServiceFactory installationServiceFactory;

    public InstallationService(InstallationServiceFactory installationServiceFactory) {
        this.installationServiceFactory = installationServiceFactory;
    }

    public ParentInstallations save(InstallationRequest request){
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        return  installationsService.save(request);
    }
}
