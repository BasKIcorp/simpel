package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Material;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpTypeForSomeInstallation;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.installation.*;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.HozPitRepository;
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
public class HozPitService implements InstallationServiceInterface<HozPitInstallation> {

    private final HozPitRepository repository;
    private final MaterialRepo materialRepo;
    private final FileStorageService fileStorageService;
    private final SearchComponent<HozPitInstallation> searchComponent;
    private final PumpRepo pumpRepo;
    private final EngineRepo engineRepo;

    public HozPitService(HozPitRepository repository, MaterialRepo materialRepo, FileStorageService fileStorageService, SearchComponent<HozPitInstallation> searchComponent, PumpRepo pumpRepo, EngineRepo engineRepo) {
        this.repository = repository;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
        this.pumpRepo = pumpRepo;
        this.engineRepo = engineRepo;
    }

    @Override
    public HozPitInstallation save(InstallationSaveRequest request, MultipartFile[] files,
                                   List<PointPressure> pointsPressure, List<PointPower> pointPower,
                                   List<PointNPSH> pointNPSH) throws IOException {
        HozPitInstallation hozPitInstallation = new HozPitInstallation();
        List<Pump> pumps = new ArrayList<>();

        // Поиск существующего двигателя по имени
        Optional<Engine> existingEngine = engineRepo.findByName(request.getEngines().get(0).getName());
        Engine engine;
        if (existingEngine.isPresent()) {
            engine = existingEngine.get();
            engine.setFieldsForPumpSave(request.getEngines().get(0));
        } else {
            engine = new Engine();
            engine.setFieldsForPumpSave(request.getEngines().get(0));
        }

        // Поиск существующего насоса по имени
        Optional<Pump> existingPump = pumpRepo.findByName(request.getPumps().get(0).getName());
        Pump pump;
        if (existingPump.isPresent()) {
            pump = existingPump.get();
            pump.setFieldsForPumpSave(request.getPumps().get(0), engine, pointsPressure, pointPower, pointNPSH);
            pump.setMaterial(materialRepo.findById(request.getMaterial().get(0)));
        } else {
            pump = new Pump();
            pump.setFieldsForPumpSave(request.getPumps().get(0), engine, pointsPressure, pointPower, pointNPSH);
            pump.setMaterial(materialRepo.findById(request.getMaterial().get(0)));
        }

        // Добавляем насос в список насосов установки
        pumps.add(pump);

        // Связываем установку с насосом
        hozPitInstallation.setPumps(pumps);
        pump.getInstallations().add(hozPitInstallation);

        // Устанавливаем общие и специфические поля для установки
        hozPitInstallation.setCommonFields(request);
        hozPitInstallation.setSpecificFields(request);
        hozPitInstallation.setFieldsForSave(request, files, fileStorageService);

        // Сохраняем данные
        engineRepo.save(engine);
        pumpRepo.save(pump);
        return repository.save(hozPitInstallation);
    }


    @Override
    public List<HozPitInstallation> getAll(InstallationRequest installationRequest) {
        HozPitInstallation hozPit = new HozPitInstallation();
        hozPit.setCommonFields(installationRequest);
        hozPit.setSpecificFields(installationRequest);
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<HozPitInstallation> suitableInstallations = !installationRequest.getPumpTypeForSomeInstallation().equals("BOTH") ?
                repository.findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndPumpTypeForSomeInstallationAndFlowRateBetween(
                        TypeInstallations.valueOf(installationRequest.getTypeInstallations()),
                        HozPitSubtypes.valueOf(installationRequest.getSubtype()),
                        CoolantType.valueOf(installationRequest.getCoolantType()),
                        installationRequest.getTemperature(),
                        installationRequest.getCountMainPumps(),
                        installationRequest.getCountSparePumps(),
                        PumpTypeForSomeInstallation.valueOf(installationRequest.getPumpTypeForSomeInstallation()),
                        minFlowRate,
                        maxFlowRate) : repository.findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndFlowRateBetween(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()),
                HozPitSubtypes.valueOf(installationRequest.getSubtype()),
                CoolantType.valueOf(installationRequest.getCoolantType()),
                installationRequest.getTemperature(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                minFlowRate,
                maxFlowRate
        );
        return searchComponent.get(suitableInstallations);
    }
}
