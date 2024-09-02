package org.simpel.pumpingUnits.controller;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.service.InstallationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/simple/inst")
public class InstallationsController {

    private final InstallationService installationService;

    public InstallationsController(InstallationService installationService) {
        this.installationService = installationService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestPart("request") InstallationRequest request,
                                  @RequestPart("files") MultipartFile[] files,
                                  @RequestPart("points")InstallationPointRequest[] pointRequests) throws IOException {
        try {
           return ResponseEntity.ok(installationService.save(request,files,pointRequests));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Some data is missing, fill out the form completely and submit again");
        }
    }
    @PostMapping(value = "/get", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getAll(@RequestPart("request") InstallationRequest request,
                                  @RequestPart("files") MultipartFile[] files) throws IOException {
        try {
            return ResponseEntity.ok(installationService.get(request));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Some data is missing, fill out the form completely and submit again");
        }
    }
}
