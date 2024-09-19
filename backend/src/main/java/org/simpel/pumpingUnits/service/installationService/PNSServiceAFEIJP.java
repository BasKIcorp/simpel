package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.installation.*;
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
    private final SearchComponent<PNSInstallationAFEIJP> searchComponent;

    public PNSServiceAFEIJP(PnsAFEIJPRepository repository, FileStorageService fileStorageService, SearchComponent<PNSInstallationAFEIJP> searchComponent) {
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
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<PNSInstallationAFEIJP> suitableInstallations = repository.findInstallations(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()).toString(),
                PNSSubtypes.valueOf(installationRequest.getSubtype()).toString(),
                CoolantType.valueOf(installationRequest.getCoolantType()).toString(),
                installationRequest.getTemperature(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                installationRequest.getTotalCapacityOfJockeyPump(),
                installationRequest.getRequiredJockeyPumpPressure(),
                maxFlowRate,
                minFlowRate);
        return searchComponent.get(suitableInstallations);
    }
}
