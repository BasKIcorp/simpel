package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.repository.GMRepository;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class GMService implements InstallationServiceInterface<GMInstallation> {

    private final GMRepository repository;
    private final FileStorageService fileStorageService;

    public GMService(GMRepository repository, FileStorageService fileStorageService) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public GMInstallation save(InstallationRequest installationRequest, MultipartFile[] files, List<InstallationPoint> points) throws IOException {
        GMInstallation gmInstallation = new GMInstallation();
        gmInstallation.setTypeInstallations(TypeInstallations.valueOf(installationRequest.getTypeInstallations()));
        gmInstallation.setSubtypes(SubtypeForGm.valueOf(installationRequest.getSubtype()));
        gmInstallation.setCoolantType(CoolantType.valueOf(installationRequest.getCoolantType()));
        gmInstallation.setConcentration(installationRequest.getConcentration());
        gmInstallation.setTemperature(installationRequest.getTemperature());
        gmInstallation.setCountMainPumps(installationRequest.getCountMainPumps());
        gmInstallation.setCountSparePumps(installationRequest.getCountSparePumps());
        gmInstallation.setFlowRate(installationRequest.getFlowRate());
        gmInstallation.setPressure(installationRequest.getPressure());
        List<String> pathFiles = fileStorageService.saveFiles(files,installationRequest.getTypeInstallations(), installationRequest.getSubtype());
        gmInstallation.setDrawingsPath(pathFiles);
        for(InstallationPoint point : points){
            point.setParentInstallations(gmInstallation);
        }
        gmInstallation.setInstallationPoints(points);
        return repository.save(gmInstallation);
    }

    @Override
    public List<GMInstallation> getAll(InstallationRequest installationRequest) {
        return repository.findAll();
    }


}
