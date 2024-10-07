package org.simpel.pumpingUnits.controller;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.service.InstallationService;
import org.simpel.pumpingUnits.service.pdfComponents.PdfComponent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/simple/inst")
public class InstallationsController {

    private final InstallationService installationService;
    private final PdfComponent pdfComponent;


    public InstallationsController(InstallationService installationService, PdfComponent pdfComponent) {
        this.installationService = installationService;
        this.pdfComponent = pdfComponent;
    }

    @PostMapping(value="/save")
    public ResponseEntity<?> save(@RequestPart("request")InstallationSaveRequest request,
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
    @PostMapping(value = "/get")
    public ResponseEntity<?> getAll(@RequestBody InstallationRequest request)throws IOException {
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
    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestParam Long installationId,
                                              @RequestParam TypeInstallations typeInstallations,
                                              @RequestParam String subtype,
                                              @RequestParam float x,
                                              @RequestParam float y) {
        try {
            byte[] pdfBytes = pdfComponent.createPdf(installationId, typeInstallations, subtype,x,y);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "generated_report.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
