package org.simpel.pumpingUnits.service.pdfComponents;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.simpel.pumpingUnits.model.Material;
import org.simpel.pumpingUnits.model.Pump;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.installation.*;
import org.simpel.pumpingUnits.service.installationService.InstallationServiceFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.itextpdf.layout.element.Paragraph;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Phaser;

@Component
public class PdfComponent<T extends ParentInstallations> {
    private final InstallationServiceFactory installationServiceFactory;
    private T installations;
    private Pump pump;
    private Material material;
    public PdfComponent(InstallationServiceFactory installationServiceFactory) {
        this.installationServiceFactory = installationServiceFactory;
    }


    public byte[] createPdf(/*List<String> options,*/ Long installationId, TypeInstallations typeInstallations, String subtype, float x, float y) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);
            // 595 x 842 x,y
            Document document = new Document(pdfDoc);
            PdfFont font = PdfFontFactory.createFont("Times New Roman.ttf", "CP1251",true);
            JpaRepository<?, Long> repository = installationServiceFactory.getRepo(typeInstallations, subtype);
            installations = (T) repository.findById(installationId).orElseThrow(() -> new NullPointerException());
            int countPumps = installations.getCountMainPumps();
            pump = installations.getPumps().get(0);
            material = pump.getMaterial();
            List<PointPressure> pointPressure = pump.getPointsPressure();
            List<PointPower> pointPowers = pump.getPointsPower();
            List<PointNPSH> pointNPSH = pump.getPointsNPSH();
            byte[] graphFirst = GraphCreated.createGraph(pointPressure, "Pressure", countPumps,x,y);
            byte[] graphSecond = GraphCreated.createGraph(pointPowers, "Power", countPumps,x,y);
            byte[] graphThird = GraphCreated.createGraph(pointNPSH, "NPSH", countPumps,x,y);

            Paragraph line1 = new Paragraph(installations.getName())
                    .setFont(font)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);

            Paragraph line2 = new Paragraph("Ваша вторая строка текста")
                    .setFont(font)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);

            // Устанавливаем позиции для строк
            line1.setFixedPosition(1,10, 800); // Позиция (x, y) для первой строки
            line2.setFixedPosition(1,10, 780); // Позиция (x, y) для второй строки

            // Добавляем строки в документ
            document.add(line1);
            document.add(line2);

        /*BufferedImage imageFirst = ImageIO.read(new ByteArrayInputStream(graphFirst));
        BufferedImage imageSecond = ImageIO.read(new ByteArrayInputStream(graphSecond));
        BufferedImage imageThird = ImageIO.read(new ByteArrayInputStream(graphThird));*/

            Image pdfImageFirst = new Image(ImageDataFactory.create(graphFirst));
            Image pdfImageSecond = new Image(ImageDataFactory.create(graphSecond));
            Image pdfImageThird = new Image(ImageDataFactory.create(graphThird));

            pdfImageFirst.setFixedPosition(10, 525);
            pdfImageSecond.setFixedPosition(10, 275);
            pdfImageThird.setFixedPosition(10, 25);

            document.add(pdfImageFirst);
            document.add(pdfImageSecond);
            document.add(pdfImageThird);



            Table infoTable = createInfoTable(font);
            infoTable.setFixedPosition(300, 275, 250);
            document.add(infoTable);

            Table pumpTable = createPumpInfoTable(font);
            pumpTable.setFixedPosition(2,50, 50, 500);
            document.add(pumpTable);

            /*Table materialTable = createMaterialTable(font);
            materialTable.setFixedPosition(2,50, 0, 500);
            document.add(materialTable);*/

            Image imageFirst = new Image(ImageDataFactory.create(installations.getDrawingsPath().get(0)));
            Image imageSecond = new Image(ImageDataFactory.create(installations.getDrawingsPath().get(1)));
            Image imageThird = new Image(ImageDataFactory.create(installations.getDrawingsPath().get(2)));

            imageFirst.setFixedPosition(3,0, 421);
            imageSecond.setFixedPosition(4,0, 421);
            imageThird.setFixedPosition(5,0, 421);

            document.add(imageFirst);
            document.add(imageSecond);
            document.add(imageThird);

            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
    private Table createInfoTable(PdfFont font) {
        Table table = new Table(2);
        Cell headerCell = new Cell(1, 2)
                .add(new Paragraph("Информация о установке"))
                .setBold()
                .setFont(font)
                .setFontSize(16)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.addHeaderCell(headerCell);
        table.setFont(font).setFontSize(14);
        table.addCell("Название насоса");
        table.addCell(pump.getName());

        table.addCell("Тип Установки");
        table.addCell(installations.getTypeInstallations().toString());

        table.addCell("Подтип Установки");
        table.addCell(installations.getSubtype().toString());

        table.addCell("Количество насосов");
        table.addCell(String.valueOf(installations.getCountMainPumps()+installations.getCountSparePumps()));

        table.addCell("Количество основных насосов");
        table.addCell(String.valueOf(installations.getCountMainPumps()));

        table.addCell("Количество резервных насосов");
        table.addCell(String.valueOf(installations.getCountSparePumps()));

        table.addCell("Тип жидкости");
        table.addCell(String.valueOf(installations.getCoolantType().getTranslation()));

        table.addCell("Температура");
        table.addCell(String.valueOf(installations.getTemperature()));

        table.addCell("Тип управления");
        table.addCell(String.valueOf(installations.getControlType().toString()));

        table.addCell("Подача");
        table.addCell(String.valueOf(installations.getFlowRate()));

        table.addCell("Напор");
        table.addCell(String.valueOf(installations.getPressure()));

        switch (installations.getClass().getSimpleName()){
            case "GMInstallation":
                GMInstallation gmInstallation = (GMInstallation) installations;
                table.addCell("Концентрация");
                if(gmInstallation.getConcentration() != null){
                    table.addCell(String.valueOf(gmInstallation.getConcentration()));
                }else {
                    table.addCell("Концентрация отсутствует");
                }
                break;
            case "HozPitInstallation"  :
                HozPitInstallation hpInstallation = (HozPitInstallation) installations;
                table.addCell("Вид насоса");
                table.addCell( hpInstallation.getPumpType().toString());
                break;
            case "PNSInstallationAFEIJP" :
                PNSInstallationAFEIJP pnsInstallation = (PNSInstallationAFEIJP) installations;
                table.addCell("Суммарная производительность жокей-насоса");
                table.addCell(String.valueOf(pnsInstallation.getTotalCapacityOfJockeyPump()));
                table.addCell("Требуемый напор жокей-насоса");
                table.addCell(String.valueOf(pnsInstallation.getRequiredJockeyPumpPressure()));
                break;
            case "PNSInstallationERW":
                PNSInstallationERW pnsInstallationErw = (PNSInstallationERW) installations;
                table.addCell("Вид насоса");
                table.addCell( pnsInstallationErw.getPumpType().toString());
        }

        return table;
    }
    private Table createPumpInfoTable(PdfFont font) {
        Table table = new Table(2);
        Cell headerCell = new Cell(1, 2)
                .add(new Paragraph("Информация о насосе"))
                .setBold()
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.addHeaderCell(headerCell);
        table.setFont(font).setFontSize(10);

        table.addCell("Артикул");
        table.addCell(pump.getName());

        table.addCell("Тип насоса");
        table.addCell(pump.getType().toString());

        table.addCell("Максимальный расход");
        table.addCell(String.valueOf(pump.getMaximumHead()));

        table.addCell("Максимальный напор");
        table.addCell(String.valueOf(pump.getMaximumPressure()));

        table.addCell("Диаметр");
        table.addCell(pump.getDiameter().toString());

        table.addCell("Патрубок на стороне всасывания");
        table.addCell(String.valueOf(pump.getDM_in()));

        table.addCell("Патрубок на напорной стороне");
        table.addCell(String.valueOf(pump.getDM_out()));

        table.addCell("Количество ступеней");
        table.addCell(String.valueOf(pump.getNumberOfSteps()));

        table.addCell("Скорость");
        table.addCell(String.valueOf(pump.getSpeed()));

        table.addCell("Кпд");
        table.addCell(String.valueOf(pump.getEfficiency()));

        table.addCell("Макс. Рабочее давление, бар (наверное NPSH)");
        table.addCell(String.valueOf(pump.getNpsh()));

        table.addCell("Установочная длина");
        table.addCell(String.valueOf(pump.getInstallationLength()));

        table.addCell("Производитель");
        if(pump.getManufacturer() != null) {
            table.addCell(pump.getManufacturer().toString());
        }else{
            table.addCell("Тут пока ничего нет");
        }

        Cell dividerCell = new Cell(1, 2)
                .add(new Paragraph("Информация про двигатель"))
                .setBold()
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.addCell(dividerCell);

        table.addCell("Мощность");
        table.addCell(String.valueOf(pump.getEngine().getPower()));

        table.addCell("Сила тока");
        table.addCell(String.valueOf(pump.getEngine().getAmperage()));

        table.addCell("Напряжение");
        table.addCell(String.valueOf(pump.getEngine().getVoltage()));

        table.addCell("Обороты");
        table.addCell(String.valueOf(pump.getEngine().getTurnovers()));

        table.addCell("Тип защиты");
        table.addCell(pump.getEngine().getTypeOfProtection());

        table.addCell("Тип изоляции");
        table.addCell(pump.getEngine().getInsulationClass());

        table.addCell("Исполнение");
        table.addCell(pump.getEngine().getExecution());

        table.addCell("Производитель");
        table.addCell(pump.getEngine().getManufacturer());

        table.addCell("Цвет");
        table.addCell(pump.getEngine().getColor());

        table.addCell("Тип насоса");
        table.addCell(pump.getEngine().getPumpType().toString());
        Cell cell = new Cell(1, 2) // Объединяем две ячейки в одну для разделителя
                .add(new Paragraph("Материалы")) // Добавляем разделительную строку или текст
                .setBold()
                .setFont(font)
                .setFontSize(14)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER); // Центрируем текст
        table.addCell(cell);

        table.addCell("Название сборки материалов");
        table.addCell(material.getName());

        table.addCell("Коллектор");
        table.addCell(material.getCollector());

        table.addCell("Запорный клапан");
        table.addCell(material.getShutOffValves());

        table.addCell("Обратный клапан");
        table.addCell(material.getCheckValves());

        table.addCell("Датчик давления");
        table.addCell(material.getPressureSensors());

        table.addCell("Реле давления");
        table.addCell(material.getPressureSwitch());

        table.addCell("Заглушки/фланцы");
        table.addCell(material.getPlugsOrFlanges());

        table.addCell("Стойка");
        table.addCell(material.getRack());

        table.addCell("Рама-основание");
        table.addCell(material.getFrameBase());

        table.addCell("Корпус насоса");
        table.addCell(material.getPumpHousing());

        table.addCell("Внешний кожух");
        table.addCell(material.getExternalCasing());

        return table;
    }
    /*private Table createMaterialTable(PdfFont font) {
        Table table = new Table(2);
        Cell headerCell = new Cell(1, 2) // Объединяем две ячейки в одну для заголовка
                .add(new Paragraph("Материалы"))
                .setBold()
                .setFont(font)
                .setFontSize(16)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.addHeaderCell(headerCell);
        table.setFont(font).setFontSize(14);
        table.setAutoLayout();
        table.addCell("Название сборки материалов");
        table.addCell(material.getName());

        table.addCell("Коллектор");
        table.addCell(material.getCollector());

        table.addCell("Запорный клапан");
        table.addCell(material.getShutOffValves());

        table.addCell("Обратный клапан");
        table.addCell(material.getCheckValves());

        table.addCell("Датчик давления");
        table.addCell(material.getPressureSensors());

        table.addCell("Реле давления");
        table.addCell(material.getPressureSwitch());

        table.addCell("Заглушки/фланцы");
        table.addCell(material.getPlugsOrFlanges());

        table.addCell("Стойка");
        table.addCell(material.getRack());

        table.addCell("Рама-основание");
        table.addCell(material.getFrameBase());

        table.addCell("Корпус насоса");
        table.addCell(material.getPumpHousing());

        table.addCell("Внешний кожух");
        table.addCell(material.getExternalCasing());
        return table;
    }*/


}
