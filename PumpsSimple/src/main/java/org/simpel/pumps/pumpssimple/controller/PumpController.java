package org.simpel.pumps.pumpssimple.controller;

import org.simpel.pumps.pumpssimple.model.Series;
import org.simpel.pumps.pumpssimple.model.enums.PumpType;
import org.simpel.pumps.pumpssimple.service.PumpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simple/")
public class PumpController {

    private final PumpService pumpService;

    public PumpController(PumpService pumpService) {
        this.pumpService = pumpService;
    }

    @GetMapping(value = "/pumps")
    public ResponseEntity<?> pumps() {
        return ResponseEntity.ok(pumpService.searchAll());
    }
    @GetMapping(value = "/search/x={x}y={y}series={series}")
    public ResponseEntity<?> pumps(@PathVariable float x, @PathVariable float y, @PathVariable String[] series) {
        try {
            return ResponseEntity.ok(pumpService.search(x, y,series));
        }catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/pump/id={id}")
    public ResponseEntity<?> pump(@PathVariable long id) {
        try {
            return ResponseEntity.ok(pumpService.getById(id));
        }catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/pumps/pumpType={pumpType}")
    public ResponseEntity<?> pumps(@PathVariable String pumpType) {
        try{
            PumpType type = PumpType.valueOf(pumpType);
            return ResponseEntity.ok(pumpService.getByPumpType(type));
        }
        catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping(value = "/pumps/series={series}")
    public ResponseEntity<?> pumpsSeries(@PathVariable String series) {
        try{
            return ResponseEntity.ok(pumpService.getByPumpSeries(series));
        }
        catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/pumpsPhoto/id={id}")
    public ResponseEntity<?> pumpsPhoto(@PathVariable long id) {
        try{
            return ResponseEntity.ok(pumpService.getPhotoByPumpId(id));
        }catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/series")
    public ResponseEntity<?> series() {
        return ResponseEntity.ok(pumpService.getAllSeries());
    }

}
