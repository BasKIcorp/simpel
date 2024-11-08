package org.simpel.pumpingUnits.controller;


import org.simpel.pumpingUnits.controller.installationsUtilsModel.*;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.PumpRepo;
import org.simpel.pumpingUnits.service.InstallationService;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simple/admin/")
public class AdminController {
    @Autowired
    private final InstallationServiceFactory installationServiceFactory;
    private final PumpRepo pumpRepo;
    private final EngineRepo engineRepo;
    private final InstallationService installationService;

    public AdminController(InstallationServiceFactory installationServiceFactory, PumpRepo pumpRepo, EngineRepo engineRepo, InstallationService installationService) {
        this.installationServiceFactory = installationServiceFactory;
        this.pumpRepo = pumpRepo;
        this.engineRepo = engineRepo;
        this.installationService = installationService;
    }


    @GetMapping("instPump")
    public ResponseEntity<?> installationsPumps(@RequestParam String type,
                                                     @RequestParam String subType) {
        try {
            List<? extends ParentInstallations> installations = (List<? extends ParentInstallations>) installationServiceFactory.getRepo(TypeInstallations.valueOf(type), subType).findAll();
            List<ListPumps> installationsList = installations.stream()
                    .map(installation -> {
                        ListPumps listPump = new ListPumps();
                        listPump.setName(installation.getPumps().get(0).getName());
                        listPump.setId(installation.getPumps().get(0).getId());
                        return listPump;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(installationsList);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (NullPointerException e){
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage():"Some data is missing, fill out the form completely and submit again");
        }


}
    @GetMapping("pumps")
    public ResponseEntity<?> pumps() {
        try{
        List<Pump> pumps = pumpRepo.findAll();
        List<ListPumps> list = pumps.stream().map(pump -> {
        ListPumps listPump = new ListPumps();
        listPump.setName(pump.getName());
        listPump.setId(pump.getId());
        return listPump;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (NullPointerException e) {
        return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
    }
    }

    @GetMapping("engines")
    public ResponseEntity<?> engines() {
        try {
        List<Engine> engines = engineRepo.findAll();
        List<ListEngine> list = engines.stream().map(engine -> {
            ListEngine item = new ListEngine();
            item.setName(engine.getName());
            item.setId(engine.getId());
            return item;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (NullPointerException e) {
        return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
    }
    }

    @GetMapping("pumps/{id}")
    public ResponseEntity<Pump> pumpById(@PathVariable Long id) {
    Pump pump = pumpRepo.findById(id).orElse(null);
    return ResponseEntity.ok(pump);
    }

    @GetMapping("engines/{id}")
    public ResponseEntity<Engine> engineById(@PathVariable Long id) {
        Engine engine = engineRepo.findById(id).orElse(null);
        return ResponseEntity.ok(engine);
    }

    @PostMapping(value="/save")
    public ResponseEntity<?> save(@RequestPart("request") InstallationSaveRequest request,
                                  @RequestPart("files") MultipartFile[] files,
                                  @RequestPart("points") InstallationPointRequest[] pointRequests) throws IOException {
        try {
            return ResponseEntity.ok(installationService.save(request, files, pointRequests));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        }
    }
}
