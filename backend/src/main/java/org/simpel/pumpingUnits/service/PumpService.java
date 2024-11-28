package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.pumpRequest.PumpRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Material;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.Series;
import org.simpel.pumpingUnits.model.installation.PointNPSH;
import org.simpel.pumpingUnits.model.installation.PointPower;
import org.simpel.pumpingUnits.model.installation.PointPressure;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.MaterialRepo;
import org.simpel.pumpingUnits.repository.PumpRepo;
import org.simpel.pumpingUnits.repository.SeriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Service
public class PumpService {
    private final PumpRepo pumpRepo;
    private final MaterialRepo materialRepo;
    private final SeriesRepository seriesRepo;

    public PumpService(PumpRepo pumpRepo, MaterialRepo materialRepo, SeriesRepository seriesRepo) {
        this.pumpRepo = pumpRepo;
        this.materialRepo = materialRepo;
        this.seriesRepo = seriesRepo;
    }


    public void save(PumpRequest request, Engine engine, MultipartFile[] photoDesign, MultipartFile[] photoDimensions, MultipartFile photo, InstallationPointRequest[] requestPoints) throws IOException {
        List<PointPressure> pointsPressure = getPointPressure(requestPoints);
        List<PointPower> pointPower = getPointPower(requestPoints);
        List<PointNPSH> pointNPSH = getPointNPSH(requestPoints);
        if (pointsPressure.size() + pointPower.size() + pointNPSH.size() == requestPoints.length) {
            Pump pump = pumpRepo.findByName(request.getPump().getName()).orElse(null);
            Material mater = materialRepo.findById(request.getMaterial()).orElse(null);
            Series serial = seriesRepo.findById(request.getSerial()).orElse(null);
            if(mater==null){
                throw new IllegalAccessError("Material not found");
            }
            if(serial==null){
                throw new IllegalAccessError("Material not found");
            }
            if (pump == null) {
                Pump newPump = new Pump();
                newPump.setFieldsSolo(request.getPump(), engine, pointsPressure, pointPower, pointNPSH, photoDesign, photoDimensions, photo,mater,serial);
                pumpRepo.save(newPump);
            } else {
                pump.setFieldsSolo(request.getPump(), engine, pointsPressure, pointPower, pointNPSH, photoDesign, photoDimensions, photo,mater,serial);
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

