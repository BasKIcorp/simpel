package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.installation.*;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.MaterialRepo;
import org.simpel.pumpingUnits.repository.PnsAFEIJPRepository;
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
public class PNSServiceAFEIJP implements InstallationServiceInterface<PNSInstallationAFEIJP> {

    private final PnsAFEIJPRepository repository;
    private final MaterialRepo materialRepo;
    private final FileStorageService fileStorageService;
    private final SearchComponent<PNSInstallationAFEIJP> searchComponent;
    private final PumpRepo pumpRepo;
    private final List<Optional<Pump>> list = new ArrayList<>();
    private final EngineRepo engineRepo;

    public PNSServiceAFEIJP(PnsAFEIJPRepository repository, MaterialRepo materialRepo, FileStorageService fileStorageService, SearchComponent<PNSInstallationAFEIJP> searchComponent, PumpRepo pumpRepo, EngineRepo engineRepo) {
        this.repository = repository;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
        this.pumpRepo = pumpRepo;
        this.engineRepo = engineRepo;
    }


    @Override
    public PNSInstallationAFEIJP save(InstallationSaveRequest request, MultipartFile[] files,
                                      List<PointPressure> pointsPressure, List<PointPower> pointPower,
                                      List<PointNPSH> pointNPSH) throws IOException {
        PNSInstallationAFEIJP pnsInstallation = new PNSInstallationAFEIJP();
        List<Pump> pumps = new ArrayList<>();

        // Поиск первого двигателя по имени
        Optional<Engine> existingEngine1 = engineRepo.findByName(request.getEngines().get(0).getName());
        Engine engine1;
        if (existingEngine1.isPresent()) {
            engine1 = existingEngine1.get();
            engine1.setFieldsForPumpSave(request.getEngines().get(0));
        } else {
            engine1 = new Engine();
            engine1.setFieldsForPumpSave(request.getEngines().get(0));
        }

        // Поиск второго двигателя по имени
        Optional<Engine> existingEngine2 = engineRepo.findByName(request.getEngines().get(1).getName());
        Engine engine2;
        if (existingEngine2.isPresent()) {
            engine2 = existingEngine2.get();
            engine2.setFieldsForPumpSave(request.getEngines().get(1));
        } else {
            engine2 = new Engine();
            engine2.setFieldsForPumpSave(request.getEngines().get(1));
        }

        // Поиск первого насоса по имени
        Pump pump1 = new Pump();
        Optional<Pump> existingPump1 = pumpRepo.findByName(request.getPumps().get(0).getName());
        if (existingPump1.isPresent()) {
            pump1 = existingPump1.get();
            pump1.setFieldsForPumpSave(request.getPumps().get(0), engine1, pointsPressure, pointPower, pointNPSH);
        } else {
            pump1.setFieldsForPumpSave(request.getPumps().get(0), engine1, pointsPressure, pointPower, pointNPSH);
        }

        // Поиск второго насоса по имени
        Pump pump2 = new Pump();
        Optional<Pump> existingPump2 = pumpRepo.findByName(request.getPumps().get(1).getName());
        if (existingPump2.isPresent()) {
            pump2 = existingPump2.get();
            pump2.setFieldsForPumpSave(request.getPumps().get(1), engine2, pointsPressure, pointPower, pointNPSH);
        } else {
            pump2.setFieldsForPumpSave(request.getPumps().get(1), engine2, pointsPressure, pointPower, pointNPSH);
        }

        // Добавляем насосы в список установки
        pumps.add(pump1);
        pumps.add(pump2);

        // Связываем установку с насосами
        pnsInstallation.setPumps(pumps);
        pump1.getInstallations().add(pnsInstallation);
        pump2.getInstallations().add(pnsInstallation);

        // Устанавливаем общие и специфические поля для установки
        pnsInstallation.setCommonFields(request);
        pnsInstallation.setSpecificFields(request);
        pnsInstallation.setFieldsForSave(request, files, fileStorageService);

        // Сохраняем данные
        engineRepo.save(engine1);
        engineRepo.save(engine2);
        pumpRepo.save(pump1);
        pumpRepo.save(pump2);
        return repository.save(pnsInstallation);
    }


    @Override
    public List<PNSInstallationAFEIJP> getAll(InstallationRequest installationRequest) {
        PNSInstallationAFEIJP pns = new PNSInstallationAFEIJP();
        pns.setCommonFields(installationRequest);
        pns.setSpecificFields(installationRequest);
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<PNSInstallationAFEIJP> suitableInstallations = repository.findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndTotalCapacityOfJockeyPumpAndRequiredJockeyPumpPressureAndFlowRateBetween(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()),
                PNSSubtypes.valueOf(installationRequest.getSubtype()),
                CoolantType.valueOf(installationRequest.getCoolantType()),
                installationRequest.getTemperature(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                installationRequest.getTotalCapacityOfJockeyPump(),
                installationRequest.getRequiredJockeyPumpPressure(),
                minFlowRate,
                maxFlowRate);
        return searchComponent.get(suitableInstallations);
    }
}
