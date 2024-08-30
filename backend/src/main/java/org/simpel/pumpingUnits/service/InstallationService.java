package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class InstallationService {

    private final InstallationServiceFactory installationServiceFactory;

    public InstallationService(InstallationServiceFactory installationServiceFactory) {
        this.installationServiceFactory = installationServiceFactory;
    }

    public ParentInstallations save(InstallationRequest request,
                                    MultipartFile[] files) throws IOException {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        return installationsService.save(request,files);
    }

    public List<?> get(InstallationRequest request) {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        return installationsService.getAll(request);
    }
}
