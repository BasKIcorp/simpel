package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InstallationService {

    private final InstallationServiceFactory installationServiceFactory;
    private final

    public InstallationService(InstallationServiceFactory installationServiceFactory) {
        this.installationServiceFactory = installationServiceFactory;
    }

    public ParentInstallations save(InstallationRequest request,
                                    MultipartFile[] files, InstallationPointRequest[] requestPoints) throws IOException {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        List<InstallationPoint> installationPoints = getPoints(requestPoints);
        return installationsService.save(request,files, installationPoints);
    }

    public List<?> get(InstallationRequest request) {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        return installationsService.getAll(request);
    }

    public List<InstallationPoint> getPoints(InstallationPointRequest[] requests) {
        List<InstallationPoint> points = new ArrayList<>();
        for (InstallationPointRequest request : requests) {
            InstallationPoint point = new InstallationPoint();
            point.setX(request.getX());
            point.setY(request.getY());
            points.add(point);
        }
        return points;
    };
}
