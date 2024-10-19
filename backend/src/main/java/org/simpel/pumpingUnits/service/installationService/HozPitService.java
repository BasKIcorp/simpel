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
import org.simpel.pumpingUnits.model.installation.*;
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

    public HozPitService(HozPitRepository repository, MaterialRepo materialRepo, FileStorageService fileStorageService, SearchComponent<HozPitInstallation>  searchComponent, PumpRepo pumpRepo) {
        this.repository = repository;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
        this.pumpRepo = pumpRepo;
    }

    @Override
    public HozPitInstallation save(InstallationSaveRequest request, MultipartFile[] files, List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) throws IOException {
        Engine engine = request.getEngines().get(0);
        Pump pump = new Pump();
        Optional<Pump> existingPump = pumpRepo.findByName(request.getPumps().get(0).getName());
        if (existingPump.isPresent()) {
            throw new NullPointerException("Имя насоса уже существует");
        }
        pump.setFieldsForPumpSave(request.getPumps().get(0), engine, pointsPressure, pointPower, pointNPSH);
        pump.setMaterial(materialRepo.findById(request.getMaterial().get(0)));
        HozPitInstallation hozPit = new HozPitInstallation();
        List<Pump> pumps = new ArrayList<>();
        pumps.add(pump);
        hozPit.setPumps(pumps);
        pump.getInstallations().add(hozPit);
        hozPit.setCommonFields(request);
        hozPit.setSpecificFields(request);
        hozPit.setFieldsForSave(request,files,fileStorageService);

        return repository.save(hozPit);
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
        List<HozPitInstallation> suitableInstallations = repository.findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndPumpTypeForSomeInstallationAndFlowRateBetween(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()),
                HozPitSubtypes.valueOf(installationRequest.getSubtype()),
                CoolantType.valueOf(installationRequest.getCoolantType()),
                installationRequest.getTemperature(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                PumpTypeForSomeInstallation.valueOf(installationRequest.getPumpTypeForSomeInstallation()),
                minFlowRate,
                maxFlowRate);
        return searchComponent.get(suitableInstallations);
    }
}
