package org.simpel.pumpingUnits.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.simpel.pumpingUnits.model.enums.Diameter;
import org.simpel.pumpingUnits.model.enums.PhotoType;
import org.simpel.pumpingUnits.model.enums.subtypes.PumpType;
import org.simpel.pumpingUnits.model.installation.*;
import org.simpel.pumpingUnits.service.FileStorageService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
public class Pump {
    public Pump() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String name;
    // новые поля для второго сайта
    @ManyToOne
    private Series series;
    private boolean availability;
    private String hydraulicSelection;
    private int liquidTemperature;
    private int ambientTemperatureMax;
    private int ambientTemperatureMin;
    private int maximumWorkingPressureBar;
    private String connectionStandard;
    private int weight;

    // поле и вложенный класс для изображений
    @OneToMany(mappedBy = "pump", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Photo> photos = new ArrayList<>();

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    private PumpType type;
    private String manufacturer;
    private int speed;
    private int numberOfSteps;
    //Максимальное давление (как я понял это и есть расход Q)
    private int maximumPressure;
    //Максимальный напор
    private float maximumHead;
    // как я понимаю диаметры должны быть и у устнановок и у насосов
    // заполняется автоматически в зависимотсти от расхода
    private Diameter diameter;
    private String article;
    private int price;
    private float power;

    private int efficiency;
    private float npsh;
    private float dm_in;
    private float dm_out;
    private float installationLength;
    private String description;

    @ManyToOne
    @JoinColumn(name = "material_name", referencedColumnName = "name")
    private Material material;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @OneToMany(mappedBy = "pump", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PointPressure> pointsPressure;

    @OneToMany(mappedBy = "pump", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PointPower> pointsPower;

    @OneToMany(mappedBy = "pump", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PointNPSH> pointsNPSH;

    @ManyToMany(mappedBy = "pumps", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private List<ParentInstallations> installations = new ArrayList<>();

    @OneToMany(mappedBy = "pump", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Detail> details = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "pump_links", joinColumns = @JoinColumn(name = "pump_id"))
    @Column(name = "link")
    private List<String> links = new ArrayList<>();

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getHydraulicSelection() {
        return hydraulicSelection;
    }

    public void setHydraulicSelection(String hydraulicSelection) {
        this.hydraulicSelection = hydraulicSelection;
    }

    public int getLiquidTemperature() {
        return liquidTemperature;
    }

    public void setLiquidTemperature(int liquidTemperature) {
        this.liquidTemperature = liquidTemperature;
    }

    public int getAmbientTemperatureMax() {
        return ambientTemperatureMax;
    }

    public void setAmbientTemperatureMax(int ambientTemperatureMax) {
        this.ambientTemperatureMax = ambientTemperatureMax;
    }

    public int getAmbientTemperatureMin() {
        return ambientTemperatureMin;
    }

    public void setAmbientTemperatureMin(int ambientTemperatureMin) {
        this.ambientTemperatureMin = ambientTemperatureMin;
    }

    public int getMaximumWorkingPressureBar() {
        return maximumWorkingPressureBar;
    }

    public void setMaximumWorkingPressureBar(int maximumWorkingPressureBar) {
        this.maximumWorkingPressureBar = maximumWorkingPressureBar;
    }

    public String getConnectionStandard() {
        return connectionStandard;
    }

    public void setConnectionStandard(String connectionStandard) {
        this.connectionStandard = connectionStandard;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public List<ParentInstallations> getInstallations() {
        return installations;
    }


    public List<PointPressure> getPointsPressure() {
        return pointsPressure;
    }

    public void setPointsPressure(List<PointPressure> pointsPressure) {
        this.pointsPressure = pointsPressure;
    }

    public List<PointPower> getPointsPower() {
        return pointsPower;
    }

    public void setPointsPower(List<PointPower> pointsPower) {
        this.pointsPower = pointsPower;
    }

    public List<PointNPSH> getPointsNPSH() {
        return pointsNPSH;
    }

    public void setPointsNPSH(List<PointNPSH> pointsNPSH) {
        this.pointsNPSH = pointsNPSH;
    }

    public void setFieldsForPumpSave(Pump pump,
                                     Engine engine,
                                     List<PointPressure> pointsPressure,
                                     List<PointPower> pointPower,
                                     List<PointNPSH> pointNPSH) {
        this.setName(pump.getName());
        this.setSpeed(pump.getSpeed());
        this.setNumberOfSteps(pump.getNumberOfSteps());
        this.setManufacturer(pump.getManufacturer());
        //наверное нужно отдельно прописать поле для насосов, но пока хз
        this.setMaximumPressure(pump.getMaximumPressure());
        this.setMaximumHead(pump.getMaximumHead());

        this.setArticle(pump.getArticle());
        this.setEfficiency(pump.getEfficiency());
        this.setNpsh(pump.getNpsh());
        this.setDm_in(pump.getDm_in());
        this.setDm_out(pump.getDm_out());
        this.setInstallationLength(pump.getInstallationLength());
        this.setDescription(pump.getDescription());

        //        this.setName(request.getNamePump());
        //        this.setSpeed(request.getSpeed());
        //        this.setNumberOfSteps(request.getNumberOfSteps());
        //        this.setManufacturer(request.getManufacturerForPump());
        //        //наверное нужно отдельно прописать поле для насосов, но пока хз
        //        this.setMaximumPressure(request.getMaximumPressure());
        //        this.setMaximumHead(request.getMaximumHead());
        //
        //        this.setArticle(request.getArticle());
        //        this.setPrice(request.getPrice());
        //        this.setEfficiency(request.getEfficiency());
        //        this.setNpsh(request.getNpsh());
        //        this.setDM_in(request.getDmIn());
        //        this.setDM_out(request.getDmOut());
        //        this.setInstallationLength(request.getInstallationLength());
        //        this.setDescription(request.getDescription());
        //добавить добавление точек
        Stream<Point> combinedStream = Stream.concat(
                Stream.concat(pointsPressure.stream(), pointPower.stream()),
                pointNPSH.stream()
        );
        combinedStream.forEach(point -> {
            point.setPump(this);
        });
        this.setPointsPressure(pointsPressure);
        this.setPointsPower(pointPower);
        this.setPointsNPSH(pointNPSH);
        this.setEngine(engine);
        this.setType(engine.getPumpType());
        this.setPrice(pump.getPrice() + engine.getPrice());

    }


    public void setFieldsSolo(Pump pump,
                              Engine engine,
                              Material material, Series series) throws IOException {

        this.setName(pump.getName());
        this.setSpeed(pump.getSpeed());
        this.setNumberOfSteps(pump.getNumberOfSteps());
        this.setManufacturer(pump.getManufacturer());
        //наверное нужно отдельно прописать поле для насосов, но пока хз
        this.setMaximumPressure(pump.getMaximumPressure());
        this.setMaximumHead(pump.getMaximumHead());

        this.setArticle(pump.getArticle());
        this.setEfficiency(pump.getEfficiency());
        this.setNpsh(pump.getNpsh());
        this.setDm_in(pump.getDm_in());
        this.setDm_out(pump.getDm_out());
        this.setInstallationLength(pump.getInstallationLength());
        this.setDescription(pump.getDescription());
        this.setEngine(engine);
        this.setType(engine.getPumpType());
        this.setPrice(pump.getPrice() + engine.getPrice());

        this.setLinks(pump.getLinks());

        this.setPower(pump.getPower());

        this.setMaterial(Optional.ofNullable(material));
        this.setSeries(series);


    }


    public void setPhotos(Pump pump, List<PointPressure> pointsPressure,
                          List<PointPower> pointPower,
                          List<PointNPSH> pointNPSH,
                          MultipartFile[] photoDesign,
                          MultipartFile[] photoDimensions,
                          MultipartFile photo, List<Detail> details) throws IOException {

        Stream<Point> combinedStream = Stream.concat(
                Stream.concat(pointsPressure.stream(), pointPower.stream()),
                pointNPSH.stream()
        );
        combinedStream.forEach(point -> {
            point.setPump(this);
        });


        this.setPointsPressure(pointsPressure);
        this.setPointsPower(pointPower);
        this.setPointsNPSH(pointNPSH);

        FileStorageService fileStorageService = new FileStorageService();

        // Сохранение фото
        savePhotos(photoDesign, PhotoType.DESIGN, fileStorageService);
        savePhotos(photoDimensions, PhotoType.DIMENSIONS, fileStorageService);

        // Сохранение главного фото
        Photo photoL = new Photo();
        photoL.setPhotoType(PhotoType.PHOTO);
        photoL.setFileName(fileStorageService.saveFile(photo, PhotoType.PHOTO));
        photoL.setPump(this);
        this.getPhotos().add(photoL);

        // Обработка деталей
        if (details != null) {
            this.setDetails(details);
        } else {
            this.setDetails(this.getDetails());
        }

        if (this.getDetails() != null) {
            for (Detail del : this.getDetails()) {
                if (del != null) {
                    del.setPump(this);
                }
            }
        }
    }

    private void savePhotos(MultipartFile[] photos, PhotoType photoType, FileStorageService fileStorageService) throws IOException {
        for (MultipartFile originalPhoto : photos) {
            Photo newPhoto = new Photo();
            newPhoto.setPhotoType(photoType);
            newPhoto.setFileName(fileStorageService.saveFile(originalPhoto, photoType));
            newPhoto.setPump(this);
            this.getPhotos().add(newPhoto);  // Добавляем фото в коллекцию
        }
    }


    public void savePoints(List<PointPressure> pointsPressure, List<PointPower> pointPower, List<PointNPSH> pointNPSH) {
        Stream<Point> combinedStream = Stream.concat(
                Stream.concat(pointsPressure.stream(), pointPower.stream()),
                pointNPSH.stream()
        );
        combinedStream.forEach(point -> point.setPump(this));

        this.getPointsPressure().addAll(pointsPressure);
        this.getPointsPower().addAll(pointPower);
        this.getPointsNPSH().addAll(pointNPSH);
        // Данные сохранены для точек, транзакция завершена
    }


    public void savePhotos(MultipartFile[] photoDesign, MultipartFile[] photoDimensions, MultipartFile photo) throws IOException {
        FileStorageService fileStorageService = new FileStorageService();

        // Сохраняем фото
        savePhotos(photoDesign, PhotoType.DESIGN, fileStorageService);
        savePhotos(photoDimensions, PhotoType.DIMENSIONS, fileStorageService);

        // Сохраняем главное фото
        Photo photoL = new Photo();
        photoL.setPhotoType(PhotoType.PHOTO);
        photoL.setFileName(fileStorageService.saveFile(photo, PhotoType.PHOTO));
        photoL.setPump(this);
        this.getPhotos().add(photoL);
        // Фото сохранены, транзакция завершена
    }


    public void saveDetails(List<Detail> details) {
        for (Detail del : details) {
            del.setPump(this);
        }
        if (details != null) {
            this.getDetails().addAll(details);
        }

    }


    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Optional<Material> material) {

        this.material = material.get();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getInstallationLength() {
        return installationLength;
    }

    public void setInstallationLength(float installationLength) {
        this.installationLength = installationLength;
    }

    public float getDm_out() {
        return dm_out;
    }

    public void setDm_out(float DM_out) {
        this.dm_out = DM_out;
    }

    public float getDm_in() {
        return dm_in;
    }

    public void setDm_in(float DM_in) {
        this.dm_in = DM_in;
    }

    public float getNpsh() {
        return npsh;
    }

    public void setNpsh(float npsh) {
        this.npsh = npsh;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Diameter getDiameter() {
        return diameter;
    }

    public void setDiameter(Diameter diameter) {
        this.diameter = diameter;
    }

    public float getMaximumHead() {
        return maximumHead;
    }

    public void setMaximumHead(float maximumHead) {
        this.maximumHead = maximumHead;
    }

    public int getMaximumPressure() {
        return maximumPressure;
    }

    public void setMaximumPressure(int maximumPressure) {
        if (maximumPressure >= 0 && maximumPressure < 16) {
            this.diameter = Diameter.DN50;
        } else if (maximumPressure >= 16 && maximumPressure < 27) {
            this.diameter = Diameter.DN65;
        } else if (maximumPressure >= 27 && maximumPressure < 38) {
            this.diameter = Diameter.DN80;
        } else if (maximumPressure >= 38 && maximumPressure < 64) {
            this.diameter = Diameter.DN100;
        } else if (maximumPressure >= 64 && maximumPressure < 98) {
            this.diameter = Diameter.DN125;
        } else if (maximumPressure >= 98 && maximumPressure < 143) {
            this.diameter = Diameter.DN150;
        } else if (maximumPressure >= 143 && maximumPressure < 243) {
            this.diameter = Diameter.DN200;
        } else if (maximumPressure >= 243 && maximumPressure < 383) {
            this.diameter = Diameter.DN250;
        } else if (maximumPressure >= 383 && maximumPressure < 542) {
            this.diameter = Diameter.DN300;
        } else if (maximumPressure >= 542 && maximumPressure < 652) {
            this.diameter = Diameter.DN350;
        }
        this.maximumPressure = maximumPressure;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public PumpType getType() {
        return type;
    }

    public void setType(PumpType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
