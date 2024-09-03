package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.PumpType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.PNSInstallationAFEIJP;
import org.simpel.pumpingUnits.model.installation.PNSInstallationERW;
import org.simpel.pumpingUnits.repository.PnsAFEIJPRepository;
import org.simpel.pumpingUnits.repository.PnsERWRepository;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class PNSServiceERW implements InstallationServiceInterface<PNSInstallationERW> {
    private final PnsERWRepository repository;
    private final FileStorageService fileStorageService;

    public PNSServiceERW(PnsERWRepository repository, FileStorageService fileStorageService) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public PNSInstallationERW save(InstallationRequest request, MultipartFile[] files, List<InstallationPoint> points) throws IOException {
        PNSInstallationERW pns = new PNSInstallationERW();
        pns.setTypeInstallations(TypeInstallations.valueOf(request.getTypeInstallations()));
        pns.setSubtypes(PNSSubtypes.valueOf(request.getSubtype()));
        pns.setCoolantType(CoolantType.valueOf(request.getCoolantType()));
        pns.setTemperature(request.getTemperature());
        pns.setCountMainPumps(request.getCountMainPumps());
        pns.setCountSparePumps(request.getCountSparePumps());
        pns.setFlowRate(request.getFlowRate());
        pns.setPressure(request.getPressure());

        pns.setPumpType(PumpType.valueOf(request.getPumpType()));

        List<String> pathFiles = fileStorageService.saveFiles(files,installationRequest.getTypeInstallations(), installationRequest.getSubtype());
        pns.setDrawingsPath(pathFiles);
        pns.setInstallationPoints(points);
        return repository.save(pns);
    }

    @Override
    public List<PNSInstallationERW> getAll(InstallationRequest parentInstallations) {
        return repository.findAll();
    }
}
