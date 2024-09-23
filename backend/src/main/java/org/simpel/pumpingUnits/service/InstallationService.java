package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.*;
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
        List<PointPressure> pointsPressure = getPointPressure(requestPoints);
        List<PointPower> pointPower = getPointPower(requestPoints);
        List<PointNPSH> pointNPSH = getPointNPSH(requestPoints);
        if (pointsPressure.size() + pointPower.size() + pointNPSH.size() == requestPoints.length) {
        return installationsService.save(request,files, pointsPressure, pointPower, pointNPSH);}
        else{
            throw new IllegalArgumentException("Нет такого типа точек либо вообще нет типа точки");
        }
    }

    public List<?> get(InstallationRequest request) {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(request.getTypeInstallations());
        InstallationServiceInterface<?> installationsService = installationServiceFactory.getInstallationService(typeInstallations, request.getSubtype());
        return installationsService.getAll(request);
    }

    public List<PointPressure> getPointPressure(InstallationPointRequest[] requests) {
        List<PointPressure> points = new ArrayList<>();
        PointPressure point = new PointPressure();
        for (InstallationPointRequest request : requests) {
            if (request.getType().equals("Pressure")) {
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            }
           /* } else if (request.getType().equals("Power")) {
                 point = new PointPower();
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            } else if (request.getType().equals("NPSH")) {
                 point = new PointNPSH();
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            }*/
        }
        return points;

    };
    public List<PointPower> getPointPower(InstallationPointRequest[] requests) {
        List<PointPower> points = new ArrayList<>();
        PointPower point = new PointPower();
        for (InstallationPointRequest request : requests) {
            if (request.getType().equals("Power")) {
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            }
        }
        return points;

    };
    public List<PointNPSH> getPointNPSH(InstallationPointRequest[] requests) {
        List<PointNPSH> points = new ArrayList<>();
        PointNPSH point = new PointNPSH();;
        for (InstallationPointRequest request : requests) {
             if (request.getType().equals("NPSH")) {
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            }


        }
        return points;

    };
}
