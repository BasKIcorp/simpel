package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.installation.InstallationPoint;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InstallationServiceInterface <T extends ParentInstallations>{
    T save(InstallationRequest parentInstallations, MultipartFile[] files, List<InstallationPoint> points) throws IOException;
    List<T> getAll(InstallationRequest parentInstallations);
    /*List<T> search(InstallationRequest parentInstallations);*/
}
