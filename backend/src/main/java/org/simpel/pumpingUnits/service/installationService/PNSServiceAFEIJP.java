package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.HozPitInstallation;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.PNSInstallationAFEIJP;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.simpel.pumpingUnits.repository.PnsAFEIJPRepository;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.simpel.pumpingUnits.service.SearchComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class PNSServiceAFEIJP implements InstallationServiceInterface <PNSInstallationAFEIJP> {

    private final PnsAFEIJPRepository repository;
    private final FileStorageService fileStorageService;
    private final SearchComponent searchComponent;

    public PNSServiceAFEIJP(PnsAFEIJPRepository repository, FileStorageService fileStorageService, SearchComponent searchComponent) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
    }

    @Override
    public PNSInstallationAFEIJP save(InstallationSaveRequest request, MultipartFile[] files, List<InstallationPoint> points) throws IOException {
        PNSInstallationAFEIJP pns = new PNSInstallationAFEIJP();
        pns.setCommonFields(request);
        pns.setSpecificFields(request);
        pns.setFieldsForSave(request,files,points,fileStorageService);
        return repository.save(pns);
    }

    @Override
    public List<PNSInstallationAFEIJP> getAll(InstallationRequest installationRequest) {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(installationRequest.getTypeInstallations());
        SubtypeForGm subtype = SubtypeForGm.valueOf(installationRequest.getSubtype());
        CoolantType coolantType = CoolantType.valueOf(installationRequest.getCoolantType());
        int temperature = installationRequest.getTemperature();
        int countMainPumps = installationRequest.getCountMainPumps();
        int countSparePumps = installationRequest.getCountSparePumps();
        PumpType pumpType = PumpType.valueOf(installationRequest.getPumpType());
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        int totalCapacityOfJockeyPump = installationRequest.getTotalCapacityOfJockeyPump();
        int requiredJockeyPumpPressure = installationRequest.getRequiredJockeyPumpPressure();
        List<PNSInstallationERW> suitableInstallations = repository.findInstallations(typeInstallations.toString(),
                subtype.toString(),
                coolantType.toString(),
                temperature,
                countMainPumps,
                countSparePumps,
                totalCapacityOfJockeyPump,
                requiredJockeyPumpPressure,
                maxFlowRate,
                minFlowRate);
        return searchComponent.get(suitableInstallations);
    }
}
