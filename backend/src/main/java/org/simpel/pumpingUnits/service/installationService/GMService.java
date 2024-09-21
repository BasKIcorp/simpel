package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.Point;
import org.simpel.pumpingUnits.repository.GMRepository;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.simpel.pumpingUnits.service.SearchComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class GMService implements InstallationServiceInterface<GMInstallation> {

    private final GMRepository repository;
    private final FileStorageService fileStorageService;
    private final SearchComponent<GMInstallation> searchComponent;

    public GMService(GMRepository repository, FileStorageService fileStorageService, SearchComponent<GMInstallation> searchComponent) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
    }

    @Override
    public GMInstallation save(InstallationSaveRequest request, MultipartFile[] files, List<Point> points) throws IOException {
        GMInstallation gmInstallation = new GMInstallation();
        gmInstallation.setCommonFields(request);
        gmInstallation.setSpecificFields(request);
        gmInstallation.setFieldsForSave(request,files,points,fileStorageService);
        return repository.save(gmInstallation);
    }

    @Override
    public List<GMInstallation> getAll(InstallationRequest installationRequest) {
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        searchComponent.setPressureForSearch(installationRequest.getPressure());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<GMInstallation> suitableInstallations = repository.findInstallations(
                TypeInstallations.valueOf(installationRequest.getTypeInstallations()).toString(),
                SubtypeForGm.valueOf(installationRequest.getSubtype()).toString(),
                CoolantType.valueOf(installationRequest.getCoolantType()).toString(),
                installationRequest.getTemperature(),
                installationRequest.getConcentration(),
                installationRequest.getCountMainPumps(),
                installationRequest.getCountSparePumps(),
                maxFlowRate,
                minFlowRate);
        return searchComponent.get(suitableInstallations);
    }


}
