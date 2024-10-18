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

    public GMService(GMRepository repository, MaterialRepo materialRepo, FileStorageService fileStorageService, SearchComponent<GMInstallation> searchComponent, PumpRepo pumpRepo) {
        this.repository = repository;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
        this.pumpRepo = pumpRepo;
    }

    @Override
    public GMInstallation save(InstallationSaveRequest request, MultipartFile[] files, List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) throws IOException {
        Engine engine = new Engine();
        engine.setFieldsForPumpSave(request);
        Pump pump = new Pump();
        Optional<Pump> existingPump = pumpRepo.findByName(request.getNamePump());
        if (existingPump.isPresent()) {
            throw new NullPointerException("Имя насоса уже существует");
        }
        pump.setFieldsForPumpSave(request, engine, pointsPressure, pointPower, pointNPSH);
        pump.setMaterial(materialRepo.findById(request.getMaterial()));
        GMInstallation gmInstallation = new GMInstallation();
        List<Pump> pumps = new ArrayList<>();
        pumps.add(pump);
        gmInstallation.setPumps(pumps);
        pump.getInstallations().add(gmInstallation);
        System.out.println("qweeqeqweqeqweqweqweqwweqweqweqweqweqweqwe");
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


}
