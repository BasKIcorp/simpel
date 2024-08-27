package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.repository.GMRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GMService implements InstallationServiceInterface<GMInstallation> {

    private final GMRepository repository;

    public GMService(GMRepository repository) {
        this.repository = repository;
    }

    @Override
    public GMInstallation save(InstallationRequest installationRequest) {
        GMInstallation gmInstallation = new GMInstallation();
        //todo сделать отодельный конструктор который будет принимать на вход InstallationRequest чтоб не засорять сервис
        gmInstallation.setTypeInstallations(TypeInstallations.valueOf(installationRequest.getTypeInstallations()));
        gmInstallation.setSubtypes(SubtypeForGm.valueOf(installationRequest.getSubtype()));
        gmInstallation.setCoolantType(CoolantType.valueOf(installationRequest.getCoolantType()));
        if (installationRequest.getConcentration() != null){
            gmInstallation.setConcentration(installationRequest.getConcentration());
        }
        gmInstallation.setTemperature(installationRequest.getTemperature());
        gmInstallation.setCountMainPumps(installationRequest.getCountMainPumps());
        gmInstallation.setCountSparePumps(installationRequest.getCountSparePumps());
        gmInstallation.setFlowRate(installationRequest.getFlowRate());
        gmInstallation.setPressure(installationRequest.getPressure());


        return repository.save(gmInstallation);
    }

    @Override
    public List<GMInstallation> getAll(InstallationRequest installationRequest) {
        return repository.findAll();
    }


}
