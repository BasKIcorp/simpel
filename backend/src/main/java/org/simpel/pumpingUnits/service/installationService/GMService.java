package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Material;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.*;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.GMRepository;
import org.simpel.pumpingUnits.repository.MaterialRepo;
import org.simpel.pumpingUnits.repository.PumpRepo;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.simpel.pumpingUnits.service.SearchComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GMService implements InstallationServiceInterface<GMInstallation> {

    private final GMRepository repository;
    private final MaterialRepo materialRepo;
    private final FileStorageService fileStorageService;
    private final SearchComponent<GMInstallation> searchComponent;
    private final PumpRepo pumpRepo;
    private final EngineRepo engineRepo;

    public GMService(GMRepository repository, MaterialRepo materialRepo, FileStorageService fileStorageService, SearchComponent<GMInstallation> searchComponent, PumpRepo pumpRepo, EngineRepo engineRepo) {
        this.repository = repository;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
        this.pumpRepo = pumpRepo;
        this.engineRepo = engineRepo;
    }

    @Override
    public GMInstallation save(InstallationSaveRequest request, MultipartFile[] files, List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) throws IOException {

        Pump pump = new Pump();
        Optional<Pump> existingPump = pumpRepo.findByName(request.getPumps().get(0).getName());
        if (existingPump.isPresent()) {
            throw new NullPointerException("Имя насоса уже существует");
        }
        pump.setFieldsForPumpSave(request.getPumps().get(0), request.getEngines().get(0), pointsPressure, pointPower, pointNPSH);
        pump.setMaterial(materialRepo.findById(request.getMaterial().get(0)));
        GMInstallation gmInstallation = new GMInstallation();
        List<Pump> pumps = new ArrayList<>();
        pumps.add(pump);
        gmInstallation.setPumps(pumps);
        pump.getInstallations().add(gmInstallation);
        gmInstallation.setCommonFields(request);
        gmInstallation.setSpecificFields(request);
        gmInstallation.setFieldsForSave(request,files,fileStorageService);

        System.out.println(gmInstallation.getTemperature());
        return repository.save(gmInstallation);
    }

    @Override
    public List<GMInstallation> getAll(InstallationRequest installationRequest) {
        GMInstallation gmInstallation = new GMInstallation();
        gmInstallation.setCommonFields(installationRequest);
        gmInstallation.setSpecificFields(installationRequest);
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<GMInstallation> suitableInstallations = repository.findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndConcentrationAndCountMainPumpsAndCountSparePumpsAndFlowRateBetween(TypeInstallations.valueOf(installationRequest.getTypeInstallations()),
                SubtypeForGm.valueOf(installationRequest.getSubtype()),
                CoolantType.valueOf(installationRequest.getCoolantType()),
                installationRequest.getTemperature(),
                installationRequest.getConcentration(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                minFlowRate,
                maxFlowRate);
        return searchComponent.get(suitableInstallations);
    }

    @Override
    public GMInstallation saveWithIds(InstallationSaveRequest request, MultipartFile[] files, List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) throws IOException {
        GMInstallation gmInstallation = new GMInstallation();
        gmInstallation.setCommonFields(request);
        gmInstallation.setSpecificFields(request);
        Pump pump = new Pump();
        if (request.getPumpIds() != null && !request.getPumpIds().isEmpty()){
            pump = pumpRepo.findById(request.getPumpIds().get(0)).orElse(null);
        }
        else {
            Optional<Pump> existingPump = pumpRepo.findByName(request.getPumps().get(0).getName());
            if (existingPump.isPresent()) {
                throw new NullPointerException("Имя насоса уже существует");
            }
            if (request.getEngineIds() != null && !request.getEngineIds().isEmpty()){
                Engine engine = engineRepo.findById(request.getEngineIds().get(0)).orElse(null);
                pump.setFieldsForPumpSave(request.getPumps().get(0), engine, pointsPressure, pointPower, pointNPSH);
            }
            else {
                pump.setFieldsForPumpSave(request.getPumps().get(0), request.getEngines().get(0), pointsPressure, pointPower, pointNPSH);
            }
            pump.setMaterial(materialRepo.findById(request.getMaterial().get(0)));
        }
        gmInstallation.getPumps().add(pump);
        pump.getInstallations().add(gmInstallation);
        gmInstallation.setFieldsForSave(request,files,fileStorageService);
        return repository.save(gmInstallation);
    }
}
