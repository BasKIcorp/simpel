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
import org.simpel.pumpingUnits.repository.PnsERWRepository;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.simpel.pumpingUnits.service.SearchComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class PNSServiceERW implements InstallationServiceInterface<PNSInstallationERW> {
    private final PnsERWRepository repository;
    private final FileStorageService fileStorageService;
    private final SearchComponent searchComponent;

    public PNSServiceERW(PnsERWRepository repository, FileStorageService fileStorageService, SearchComponent searchComponent) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
    }

    @Override
    public PNSInstallationERW save(InstallationSaveRequest request, MultipartFile[] files, List<InstallationPoint> points) throws IOException {
        PNSInstallationERW pns = new PNSInstallationERW();
        pns.setCommonFields(request);
        pns.setSpecificFields(request);
        pns.setFieldsForSave(request,files,points,fileStorageService);
        return repository.save(pns);
    }

    @Override
    public List<PNSInstallationERW> getAll(InstallationRequest installationRequest) {
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<PNSInstallationERW> suitableInstallations = repository.findInstallations(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()).toString(),
                SubtypeForGm.valueOf(installationRequest.getSubtype()).toString(),
                CoolantType.valueOf(installationRequest.getCoolantType()).toString(),
                installationRequest.getTemperature(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                PumpType.valueOf(installationRequest.getPumpType()).toString(),
                maxFlowRate,
                minFlowRate);
        return searchComponent.get(suitableInstallations);
    }
}
