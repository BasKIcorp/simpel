package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.Point;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstallationService {

    private final InstallationServiceFactory installationServiceFactory;


    public InstallationService(InstallationServiceFactory installationServiceFactory) {
        this.installationServiceFactory = installationServiceFactory;

    }

    public ParentInstallations save(InstallationSaveRequest request,
                                    MultipartFile[] files, InstallationPointRequest[] requestPoints) throws IOException {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        List<Point> points = getPoints(requestPoints);
        return installationsService.save(request,files, points);
    }

    public List<?> get(InstallationRequest request) {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        return installationsService.getAll(request);
    }

    public List<Point> getPoints(InstallationPointRequest[] requests) {
        List<Point> points = new ArrayList<>();
        for (InstallationPointRequest request : requests) {
            Point point = new Point();
            point.setX(request.getX());
            point.setY(request.getY());
            points.add(point);
        }
        return points;

    };
}
