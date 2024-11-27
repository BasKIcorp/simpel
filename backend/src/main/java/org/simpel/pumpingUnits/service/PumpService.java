package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.installation.PointNPSH;
import org.simpel.pumpingUnits.model.installation.PointPower;
import org.simpel.pumpingUnits.model.installation.PointPressure;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.PumpRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PumpService {
    private final PumpRepo pumpRepo;

    public PumpService(PumpRepo pumpRepo) {
        this.pumpRepo = pumpRepo;
    }


    public void save(Pump request, Engine engine, MultipartFile[] photoDesign, MultipartFile[] photoDimensions, MultipartFile photo, InstallationPointRequest[] requestPoints) throws IOException {
        List<PointPressure> pointsPressure = getPointPressure(requestPoints);
        List<PointPower> pointPower = getPointPower(requestPoints);
        List<PointNPSH> pointNPSH = getPointNPSH(requestPoints);
        if (pointsPressure.size() + pointPower.size() + pointNPSH.size() == requestPoints.length) {
            Pump pump = pumpRepo.findByName(request.getName()).orElse(null);
            if (pump == null) {
                Pump newPump = new Pump();
                newPump.setFieldsSolo(request, engine, pointsPressure, pointPower, pointNPSH, photoDesign, photoDimensions, photo);
                pumpRepo.save(newPump);
            } else {
                pump.setFieldsSolo(request, engine, pointsPressure, pointPower, pointNPSH, photoDesign, photoDimensions, photo);
                pumpRepo.save(pump);
            }
        } else {
            throw new IllegalArgumentException("Нет такого типа точек либо вообще нет типа точки");
        }

    }

    public List<PointPressure> getPointPressure(InstallationPointRequest[] requests) {
        List<PointPressure> points = new ArrayList<>();
        for (InstallationPointRequest request : requests) {
            if (request.getType().equals("Pressure")) {
                PointPressure point = new PointPressure();
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            }

        }
        return points;

    }

    ;

    public List<PointPower> getPointPower(InstallationPointRequest[] requests) {
        List<PointPower> points = new ArrayList<>();

        for (InstallationPointRequest request : requests) {
            if (request.getType().equals("Power")) {
                PointPower point = new PointPower();
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            }
        }
        return points;

    }

    ;

    public List<PointNPSH> getPointNPSH(InstallationPointRequest[] requests) {
        List<PointNPSH> points = new ArrayList<>();

        for (InstallationPointRequest request : requests) {
            if (request.getType().equals("NPSH")) {
                PointNPSH point = new PointNPSH();
                point.setX(request.getX());
                point.setY(request.getY());
                points.add(point);
            }
        }
        return points;

    }
}

