package org.simpel.pumpingUnits.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.pumpRequest.PumpRequest;
import org.simpel.pumpingUnits.model.*;
import org.simpel.pumpingUnits.model.installation.PointNPSH;
import org.simpel.pumpingUnits.model.installation.PointPower;
import org.simpel.pumpingUnits.model.installation.PointPressure;
import org.simpel.pumpingUnits.repository.CategoryRepository;
import org.simpel.pumpingUnits.repository.MaterialRepo;
import org.simpel.pumpingUnits.repository.PumpRepo;
import org.simpel.pumpingUnits.repository.SeriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class PumpService {
    private final PumpRepo pumpRepo;
    private final MaterialRepo materialRepo;
    private final SeriesRepository seriesRepo;
    private final CategoryRepository categoryRepository;

    public PumpService(PumpRepo pumpRepo, MaterialRepo materialRepo, SeriesRepository seriesRepo, CategoryRepository categoryRepository) {
        this.pumpRepo = pumpRepo;
        this.materialRepo = materialRepo;
        this.seriesRepo = seriesRepo;
        this.categoryRepository = categoryRepository;
    }


    public void save(PumpRequest request, Engine engine, MultipartFile[] photoDesign, MultipartFile[] photoDimensions, MultipartFile photo, InstallationPointRequest[] requestPoints) throws IOException {
        List<PointPressure> pointsPressure = getPointPressure(requestPoints);
        List<PointPower> pointPower = getPointPower(requestPoints);
        List<PointNPSH> pointNPSH = getPointNPSH(requestPoints);
        if (pointsPressure.size() + pointPower.size() + pointNPSH.size() == requestPoints.length) {
            Pump pump = pumpRepo.findByName(request.getPump().getName()).orElse(null);
            Material mater = materialRepo.findById(request.getMaterial()).orElse(null);
            Series serial = seriesRepo.findById(request.getSerial()).orElse(null);
            if (mater == null) {
                throw new IllegalAccessError("Material not found");
            }
            if (serial == null) {
                throw new IllegalAccessError("Series not found");
            }
            if (pump == null) {
                Pump newPump = new Pump();
                newPump.setFieldsSolo(request.getPump(), engine, mater, serial);
                newPump = pumpRepo.save(newPump);
                newPump.saveDetails(request.getDetails());
                newPump = pumpRepo.save(newPump);
                newPump.savePhotos(photoDesign, photoDimensions, photo);
                newPump = pumpRepo.save(newPump);
                newPump.savePoints(pointsPressure, pointPower, pointNPSH);
                pumpRepo.save(newPump);

            } else {
                pump.setFieldsSolo(request.getPump(), engine, mater, serial);
                pump = pumpRepo.save(pump);
                pump.savePoints(pointsPressure, pointPower, pointNPSH);
                pump = pumpRepo.save(pump);
                pump.savePhotos(photoDesign, photoDimensions, photo);
                pump = pumpRepo.save(pump);
                pump.saveDetails(request.getDetails());
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

    public void saveNewMaterial(Material materialRequest) {
        if (materialRepo.findById(materialRequest.getName()).orElse(null) != null) {
            throw new IllegalArgumentException("Конфигурация материалов с таким названием уже существует");
        }
        Material material = new Material();
        material.setFields(materialRequest);
        validateNotNullFields(material);
        materialRepo.save(material);
    }

    public void validateNotNullFields(Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NotNull.class)) {
                try {
                    Object value = field.get(object);
                    if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                        throw new IllegalArgumentException(
                                String.format("Field '%s' cannot be null or empty", field.getName())
                        );
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field: " + field.getName(), e);
                }
            }
        }
    }

    public List<Series> getSeries() {
        return seriesRepo.findAll();
    }

    public void saveSeries(String name, String cat) {
        Series series = new Series();
        Category category = categoryRepository.findById(cat).orElseThrow(IllegalArgumentException::new);
        series.setName(name);
        series.setCategoryName(category);
        seriesRepo.save(series);
    }
}

