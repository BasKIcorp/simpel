package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.HozPitInstallation;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.simpel.pumpingUnits.repository.HozPitRepository;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.simpel.pumpingUnits.service.SearchComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class HozPitService implements InstallationServiceInterface<HozPitInstallation> {

    private final HozPitRepository repository;
    private final FileStorageService fileStorageService;
    private final SearchComponent<HozPitInstallation> searchComponent;

    public HozPitService(HozPitRepository repository, FileStorageService fileStorageService, SearchComponent<HozPitInstallation>  searchComponent) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
    }

    @Override
    public HozPitInstallation save(InstallationSaveRequest request, MultipartFile[] files, List<InstallationPoint> points) throws IOException {
        HozPitInstallation hpi = new HozPitInstallation();
        hpi.setCommonFields(request);
        hpi.setSpecificFields(request);
        hpi.setFieldsForSave(request,files,points,fileStorageService);
        return repository.save(hpi);
    }

    @Override
    public List<HozPitInstallation> getAll(InstallationRequest installationRequest) {
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<HozPitInstallation> suitableInstallations = repository.findInstallations(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()).toString(),
                HozPitSubtypes.valueOf(installationRequest.getSubtype()).toString(),
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
