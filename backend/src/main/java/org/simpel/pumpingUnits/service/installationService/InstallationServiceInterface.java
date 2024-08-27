package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;

import java.util.List;

public interface InstallationServiceInterface <T extends ParentInstallations>{
    T save(InstallationRequest parentInstallations);
    List<T> getAll(InstallationRequest parentInstallations);
}
