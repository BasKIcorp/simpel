package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;
import org.simpel.pumpingUnits.model.installation.HozPitInstallation;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.repository.HozPitRepository;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class HozPitService implements InstallationServiceInterface<HozPitInstallation> {

    private final HozPitRepository repository;
    private final FileStorageService fileStorageService;

    public HozPitService(HozPitRepository repository, FileStorageService fileStorageService) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public HozPitInstallation save(InstallationRequest request, MultipartFile[] files, List<InstallationPoint> points) throws IOException {
        HozPitInstallation hpi = new HozPitInstallation();
        hpi.setTypeInstallations(TypeInstallations.valueOf(request.getTypeInstallations()));
        hpi.setSubtypes(HozPitSubtypes.valueOf(request.getSubtype()));
        hpi.setCoolantType(CoolantType.valueOf(request.getCoolantType()));
        hpi.setTemperature(request.getTemperature());
        hpi.setTemperature(request.getTemperature());
        hpi.setCountMainPumps(request.getCountMainPumps());
        hpi.setCountSparePumps(request.getCountSparePumps());
        hpi.setFlowRate(request.getFlowRate());
        hpi.setPressure(request.getPressure());

        hpi.setPumpType(PumpType.valueOf(request.getPumpType()));
        List<String> pathFiles = fileStorageService.saveFiles(files,request.getTypeInstallations(), request.getSubtype());
        hpi.setDrawingsPath(pathFiles);
        hpi.setInstallationPoints(points);
        return repository.save(hpi);
    }

    @Override
    public List<HozPitInstallation> getAll(InstallationRequest parentInstallations) {
        return repository.findAll();
    }
}
