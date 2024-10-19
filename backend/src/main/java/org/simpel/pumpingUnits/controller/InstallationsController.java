package org.simpel.pumpingUnits.controller;

import org.simpel.pumpingUnits.controller.installationsUtilsModel.GeneratePdfRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.service.InstallationService;
import org.simpel.pumpingUnits.service.pdfComponents.PdfComponent;
import org.simpel.pumpingUnits.service.post.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/simple/inst")
public class InstallationsController {

    private final InstallationService installationService;
    private final PdfComponent pdfComponent;
    private final PostService postService;


    public InstallationsController(InstallationService installationService, PdfComponent pdfComponent, PostService postService) {
        this.installationService = installationService;
        this.pdfComponent = pdfComponent;
        this.postService = postService;
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
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage():"Some data is missing, fill out the form completely and submit again");
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
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody GeneratePdfRequest request) {
        try {
            byte[] pdfBytes = pdfComponent.createPdf(
                    request.getOptions(),
                    request.getInstallationId(),
                    request.getTypeInstallations(),
                    request.getSubtype(),
                    request.getX(),
                    request.getY()
            );

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
    @PostMapping("/generateAndSend")
    public ResponseEntity<?> generatePdfAndSend(@RequestBody GeneratePdfRequest request) {
        try {
            byte[] pdfBytes = pdfComponent.createPdf(
                    request.getOptions(),
                    request.getInstallationId(),
                    request.getTypeInstallations(),
                    request.getSubtype(),
                    request.getX(),
                    request.getY()
            );
                postService.sendPdf(pdfBytes);
//                postService.sendUsers();

            return ResponseEntity.ok("Письмо успешно отправлено");

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ошибка при отправке письма", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
