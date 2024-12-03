package org.simpel.pumps.pumpssimple.service;

import org.simpel.pumps.pumpssimple.model.Category;
import org.simpel.pumps.pumpssimple.model.Photo;
import org.simpel.pumps.pumpssimple.model.Pump;
import org.simpel.pumps.pumpssimple.model.Series;
import org.simpel.pumps.pumpssimple.model.enums.PumpType;
import org.simpel.pumps.pumpssimple.repository.CategoryRepository;
import org.simpel.pumps.pumpssimple.repository.PhotoRepository;
import org.simpel.pumps.pumpssimple.repository.PumpRepository;
import org.simpel.pumps.pumpssimple.repository.SeriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PumpService {
    private final PumpRepository pumpRepository;
    private final CategoryRepository categoryRepository;
    private final SeriesRepository seriesRepository;
    private final PhotoRepository photoRepository;
    private final SearchComponent searchComponent;

    public PumpService(PumpRepository pumpRepository, CategoryRepository categoryRepository, SeriesRepository seriesRepository, PhotoRepository photoRepository, SearchComponent searchComponent) {
        this.pumpRepository = pumpRepository;
        this.categoryRepository = categoryRepository;
        this.seriesRepository = seriesRepository;
        this.photoRepository = photoRepository;
        this.searchComponent = searchComponent;
    }

    public List<Pump> search(float x, float y, String[] seriesList) {
        // Устанавливаем параметры для поиска
        searchComponent.setFlowRateForSearch(x);
        searchComponent.setPressureForSearch(y);

        // Проверяем, что список серий не пуст
        if (seriesList == null || seriesList.length==0) {
            throw new IllegalArgumentException("Список серий не должен быть пустым");
        }

        // Получаем все Series из базы данных по переданным ID

        List<Series> seriesEntities = seriesRepository.findAllById(List.of(seriesList));
        if (seriesEntities.isEmpty()) {
            throw new IllegalArgumentException("Не найдены серии с указанными идентификаторами");
        }

        // Ищем насосы по найденным сериям
        List<Pump> allPumps = pumpRepository.findBySeriesIn(seriesEntities);

        // Применяем фильтрацию через searchComponent
        return searchComponent.get(allPumps);
    }

    public List<Pump> searchAll() {
        return pumpRepository.findAll();
    }

    public List<Pump> getById(long id) {
        ArrayList<Pump> pumps = new ArrayList<>();
        pumps.add(pumpRepository.findById(id).orElseThrow(NullPointerException::new));
        return pumps;
    }

    public List<Pump> getByPumpType(PumpType pumpType) {
        List<Pump> pumpList = pumpRepository.findByType(pumpType);
        if (pumpList.isEmpty()) {
            throw new NullPointerException("No pump found for type " + pumpType);
        }
        return pumpList;
    }
    public List<Pump> getByPumpSeries(String name) {
        Series series = seriesRepository.findById(name).orElseThrow(IllegalAccessError::new);
        List<Pump> pumpList = pumpRepository.findBySeries(series);
        if (pumpList.isEmpty()) {
            throw new NullPointerException("No pump found for series " + series);
        }
        return pumpList;
    }

    public List<Photo> getPhotoByPumpId(long id) {
        List<Photo> photoList = photoRepository.findByPumpId(id);
        if (photoList.isEmpty()) {
            throw new NullPointerException("No Photo found for Pump " + id);
        }
        return photoList;
    }

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }
 }
