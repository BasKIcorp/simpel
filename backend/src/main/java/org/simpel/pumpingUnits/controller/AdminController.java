package org.simpel.pumpingUnits.controller;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationPointRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.InstallationSaveRequest;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.ListEngine;
import org.simpel.pumpingUnits.controller.installationsUtilsModel.ListPumps;
import org.simpel.pumpingUnits.controller.pumpRequest.PumpRequest;
import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.model.Material;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.simpel.pumpingUnits.repository.MaterialRepo;
import org.simpel.pumpingUnits.repository.PumpRepo;
import org.simpel.pumpingUnits.service.EngineService;
import org.simpel.pumpingUnits.service.InstallationService;
import org.simpel.pumpingUnits.service.PumpService;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.simpel.pumpingUnits.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simple/admin/")
@CrossOrigin(origins = {"http://localhost:3000", "http://51.250.25.148:3000", "https://web.telegram.org", "https://t.me", "https://telegram.me", "https://telegram.org"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AdminController {
    @Autowired
    private final InstallationServiceFactory installationServiceFactory;
    private final PumpRepo pumpRepo;
    private final EngineRepo engineRepo;
    private final InstallationService installationService;
    private final MaterialRepo materialRepo;
    private final PostService postService;
    private final EngineService engineService;
    private final PumpService pumpService;

    public AdminController(InstallationServiceFactory installationServiceFactory, PumpRepo pumpRepo, EngineRepo engineRepo, InstallationService installationService, MaterialRepo materialRepo, PostService postService, EngineService engineService, PumpService pumpService) {
        this.installationServiceFactory = installationServiceFactory;
        this.pumpRepo = pumpRepo;
        this.engineRepo = engineRepo;
        this.installationService = installationService;
        this.materialRepo = materialRepo;
        this.postService = postService;
        this.engineService = engineService;
        this.pumpService = pumpService;
    }


    @GetMapping("instPump")
    public ResponseEntity<?> installationsPumps(@RequestParam String type, @RequestParam String subType) {
        try {
            List<? extends ParentInstallations> installations = (List<? extends ParentInstallations>) installationServiceFactory.getRepo(TypeInstallations.valueOf(type), subType).findAll();
            List<ListPumps> installationsList = installations.stream().map(installation -> {
                ListPumps listPump = new ListPumps();
                listPump.setName(installation.getPumps().get(0).getName());
                listPump.setId(installation.getPumps().get(0).getId());
                return listPump;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(installationsList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        }


    }

    @GetMapping("pumps")
    public ResponseEntity<?> pumps() {
        try {
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
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
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
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        }

    }

    @GetMapping("pumps/{id}")
    public ResponseEntity<?> pumpById(@PathVariable Long id) {
        try {
            Pump pump = pumpRepo.findById(id).orElse(null);
            return ResponseEntity.ok(pump);
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        }
    }

    @GetMapping("engines/{id}")
    public ResponseEntity<?> engineById(@PathVariable Long id) {
        try {
            Engine engine = engineRepo.findById(id).orElse(null);
            return ResponseEntity.ok(engine);
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        }
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@RequestPart("request") InstallationSaveRequest request, @RequestPart("files") MultipartFile[] files, @RequestPart("points") InstallationPointRequest[] pointRequests) throws IOException {
        try {
            return ResponseEntity.ok(installationService.save(request, files, pointRequests));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("users")
    public ResponseEntity<?> usersOnEmail() throws IOException {
        try {
            postService.sendUsers();
            return ResponseEntity.ok().body("ок");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        }

    }

    @GetMapping("/materials")
    public ResponseEntity<?> materials() {
        try {
            List<String> materials = materialRepo.findAll().stream().map(material -> {
                return material.getName();
            }).collect(Collectors.toList());

            return ResponseEntity.ok(materials);
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        }
    }

    @PostMapping("/save/engine")
    public ResponseEntity<?> saveEngine(@RequestBody Engine engine) throws IOException {
        try {
            engineService.save(engine);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Успешно");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        }
    }

    @PostMapping("/save/pump")
    public ResponseEntity<?> savePump(@RequestPart("pump") PumpRequest pump,
                                      @RequestPart("engine") Engine engine,
                                      @RequestPart("points") InstallationPointRequest[] pointRequests,
                                      @RequestPart("photoDesign") MultipartFile[] photoDesign,
                                      @RequestPart("photoDimensions") MultipartFile[] photoDimensions,
                                      @RequestPart("photo") MultipartFile photo
    ) throws IOException {
        try {
            Engine engine1 = engineRepo.findByName(engine.getName()).orElse(null);

            if (engine1 != null) {
                pumpService.save(pump, engine1, photoDesign, photoDimensions, photo, pointRequests);
                return ResponseEntity.ok("Success");
            } else {
                Engine engine2 = new Engine();
                engine2.setFieldsForPumpSave(engine);
                pumpService.save(pump, engine2, photoDesign, photoDimensions, photo, pointRequests);
                return ResponseEntity.ok("Success");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        } catch (MalformedJwtException | ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный или истекший токен");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ошибка авторизации");
        }
    }

    @PostMapping("save/material")
    public ResponseEntity<?> saveMaterial(@RequestBody Material material) {
        try {
            pumpService.saveNewMaterial(material);
            return ResponseEntity.ok("Success");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("series")
    public ResponseEntity<?> getSeries() {
        return ResponseEntity.ok(pumpService.getSeries());
    }

    @PostMapping("save/series/{name}/{cat}")
    public ResponseEntity<?> saveSeries(@PathVariable String name, @PathVariable String cat) {
        try {
            pumpService.saveSeries(name, cat);
            return ResponseEntity.ok("Success");
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage() != "" ? e.getMessage() : "Some data is missing, fill out the form completely and submit again");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
