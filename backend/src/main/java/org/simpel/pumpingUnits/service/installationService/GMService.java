package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
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
    private final SearchComponent searchComponent;

    public GMService(GMRepository repository, FileStorageService fileStorageService, SearchComponent searchComponent) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.searchComponent = searchComponent;
    }

    @Override
    public GMInstallation save(InstallationSaveRequest request, MultipartFile[] files, List<InstallationPoint> points) throws IOException {
        GMInstallation gmInstallation = new GMInstallation();
        gmInstallation.setCommonFields(request);
        gmInstallation.setSpecificFields(request);
        gmInstallation.setFieldsForSave(request,files,points,fileStorageService);
        return repository.save(gmInstallation);
    }

    @Override
    public List<GMInstallation> getAll(InstallationRequest installationRequest) {
        TypeInstallations typeInstallations = TypeInstallations.valueOf(installationRequest.getTypeInstallations());
        SubtypeForGm subtype = SubtypeForGm.valueOf(installationRequest.getSubtype());
        CoolantType coolantType = CoolantType.valueOf(installationRequest.getCoolantType());
        int temperature = installationRequest.getTemperature();
        int concentration = installationRequest.getConcentration();
        int countMainPumps = installationRequest.getCountMainPumps();
        int countSparePumps = installationRequest.getCountSparePumps();
        searchComponent.setFlowRateForSearch(installationRequest.getFlowRate());
        int maxFlowRate = searchComponent.getMaxFlowRate();
        int minFlowRate = searchComponent.getMinFlowRate();
        List<GMInstallation> suitableInstallations = repository.findInstallations(typeInstallations.toString(),
                subtype.toString(),
                coolantType.toString(),
                temperature,
                concentration,
                countMainPumps,
                countSparePumps,
                maxFlowRate,
                minFlowRate);
        return searchComponent.get(suitableInstallations);
    }


}
