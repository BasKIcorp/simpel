package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.installation.Point;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InstallationServiceInterface <T extends ParentInstallations>{
    T save(InstallationSaveRequest parentInstallations, MultipartFile[] files, List<Point> points) throws IOException;
    List<T> getAll(InstallationRequest installationRequest);
    /*List<T> search(InstallationRequest parentInstallations);*/
}
