package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Material;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpTypeForSomeInstallation;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.*;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.MaterialRepo;
import org.simpel.pumpingUnits.repository.PnsERWRepository;
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
public class PNSServiceERW implements InstallationServiceInterface<PNSInstallationERW> {
    private final PnsERWRepository repository;
    private final MaterialRepo materialRepo;
    private final FileStorageService fileStorageService;
    private final SearchComponent<PNSInstallationERW> searchComponent;
    private final PumpRepo pumpRepo;
    private final EngineRepo engineRepo;

    public PNSServiceERW(PnsERWRepository repository, MaterialRepo materialRepo, FileStorageService fileStorageService, SearchComponent<PNSInstallationERW> searchComponent, PumpRepo pumpRepo, EngineRepo engineRepo) {
        this.repository = repository;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
        this.pumpRepo = pumpRepo;
        this.engineRepo = engineRepo;
    }

    @Override
    public PNSInstallationERW save(InstallationSaveRequest request, MultipartFile[] files, List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) throws IOException {
        Engine engine = request.getEngines().get(0);
        Pump pump = new Pump();
        Optional<Pump> existingPump = pumpRepo.findByName(request.getPumps().get(0).getName());
        if (existingPump.isPresent()) {
            throw new NullPointerException("Имя насоса уже существует");
        }
        pump.setFieldsForPumpSave(request.getPumps().get(0), engine, pointsPressure, pointPower, pointNPSH);
        pump.setMaterial(materialRepo.findById(request.getMaterial().get(0)));
        PNSInstallationERW pns = new PNSInstallationERW();
        List<Pump> pumps = new ArrayList<>();
        pumps.add(pump);
        pns.setPumps(pumps);
        pump.getInstallations().add(pns);
        pns.setCommonFields(request);
        pns.setSpecificFields(request);
        pns.setFieldsForSave(request,files,fileStorageService);
        return repository.save(pns);
    }

    @Override
    public List<PNSInstallationERW> getAll(InstallationRequest installationRequest) {
        PNSInstallationERW pns = new PNSInstallationERW();
        pns.setCommonFields(installationRequest);
        pns.setSpecificFields(installationRequest);
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<PNSInstallationERW> suitableInstallations = !installationRequest.getPumpTypeForSomeInstallation().equals("BOTH")?
                repository.findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndPumpTypeForSomeInstallationAndFlowRateBetween(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()),
                PNSSubtypes.valueOf(installationRequest.getSubtype()),
                CoolantType.valueOf(installationRequest.getCoolantType()),
                installationRequest.getTemperature(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                PumpTypeForSomeInstallation.valueOf(installationRequest.getPumpTypeForSomeInstallation()),
                minFlowRate,
                maxFlowRate) : repository.findByTypeInstallationsAndSubtypeAndCoolantTypeAndTemperatureAndCountMainPumpsAndCountSparePumpsAndFlowRateBetween(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()),
                PNSSubtypes.valueOf(installationRequest.getSubtype()),
                CoolantType.valueOf(installationRequest.getCoolantType()),
                installationRequest.getTemperature(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                minFlowRate,
                maxFlowRate
        );
        return searchComponent.get(suitableInstallations);
    }

    @Override
    public PNSInstallationERW saveWithIds(InstallationSaveRequest request, MultipartFile[] files, List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) throws IOException {
        PNSInstallationERW pns = new PNSInstallationERW();
        pns.setCommonFields(request);
        pns.setSpecificFields(request);
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
        pns.getPumps().add(pump);
        pump.getInstallations().add(pns);
        pns.setFieldsForSave(request,files,fileStorageService);
        return repository.save(pns);
    }
}
