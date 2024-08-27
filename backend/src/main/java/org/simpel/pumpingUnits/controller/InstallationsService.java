package org.simpel.pumpingUnits.controller;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.service.InstallationService;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simple/inst")
public class InstallationsService {

    private final InstallationService installationService;

    public InstallationsService(InstallationService installationService) {
        this.installationService = installationService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody InstallationRequest request) {
        try {
           return ResponseEntity.ok(installationService.save(request));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
