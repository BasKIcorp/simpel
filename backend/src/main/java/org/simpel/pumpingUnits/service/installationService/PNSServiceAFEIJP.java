package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.installation.*;
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
public class PNSServiceAFEIJP implements InstallationServiceInterface <PNSInstallationAFEIJP> {

    private final PnsAFEIJPRepository repository;
    private final MaterialRepo materialRepo;
    private final FileStorageService fileStorageService;
    private final SearchComponent<PNSInstallationAFEIJP> searchComponent;
    private final PumpRepo pumpRepo;
    private final List<Optional<Pump>> list = new ArrayList<>();

    public PNSServiceAFEIJP(PnsAFEIJPRepository repository, MaterialRepo materialRepo, FileStorageService fileStorageService, SearchComponent<PNSInstallationAFEIJP> searchComponent, PumpRepo pumpRepo) {
        this.repository = repository;
        this.materialRepo = materialRepo;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
        this.pumpRepo = pumpRepo;
    }


    @Override
    public PNSInstallationAFEIJP save(InstallationSaveRequest request, MultipartFile[] files, List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) throws IOException {
        List<Pump> pumps = new ArrayList<>();
        PNSInstallationAFEIJP pns = new PNSInstallationAFEIJP();
        Optional<Pump> existingPump = pumpRepo.findByName(request.getPumps().get(0).getName());
        Optional<Pump> existingPumpJok = pumpRepo.findByName(request.getPumps().get(1).getName());
        list.add(existingPump);
        list.add(existingPumpJok);
        for(Optional<Pump> pumpik : list  ) {
            if (pumpik.isPresent()) {
                throw new NullPointerException("Имя насоса уже существует");
            }
        }
        Engine engine = request.getEngines().get(0);
        Pump pump = new Pump();
        pump.setFieldsForPumpSave(request.getPumps().get(0), engine, pointsPressure, pointPower, pointNPSH);
        pump.setMaterial(materialRepo.findById(request.getMaterial().get(0)));
        pumps.add(pump);

        Engine engineJok = request.getEngines().get(1);
        Pump pumpJok = new Pump();
        pumpJok.setFieldsForPumpSave(request.getPumps().get(1), engineJok, pointsPressure, pointPower, pointNPSH);
        pumpJok.setMaterial(materialRepo.findById(request.getMaterial().get(1)));
        pumps.add(pumpJok);

        pns.setPumps(pumps);
        pump.getInstallations().add(pns);
        pumpJok.getInstallations().add(pns);
        pns.setCommonFields(request);
        pns.setSpecificFields(request);
        pns.setFieldsForSave(request,files,fileStorageService);

        return repository.save(pns);
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
