package org.simpel.pumpingUnits.service;

import org.simpel.pumpingUnits.model.Engine;
import org.simpel.pumpingUnits.repository.EngineRepo;
import org.springframework.stereotype.Service;

@Service
public class EngineService {
    private final EngineRepo engineRepo;

    public EngineService(EngineRepo engineRepo) {
        this.engineRepo = engineRepo;
    }

    public void save(Engine request) {
        Engine engine = engineRepo.findByName(request.getName()).orElse(null);
        if (engine == null) {
            Engine newEngine = new Engine();
            newEngine.setFieldsForPumpSave(request);
            engineRepo.save(newEngine);
        } else {
            engine.setFieldsForPumpSave(request);
            engineRepo.save(engine);
        }
    }

}
