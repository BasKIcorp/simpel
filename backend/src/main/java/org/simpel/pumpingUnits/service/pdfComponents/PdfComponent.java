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
import org.simpel.pumpingUnits.model.enums.CoolantType;
import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.HozPitSubtypes;
import org.simpel.pumpingUnits.model.enums.subtypes.PNSSubtypes;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
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


    public byte[] createPdf(Options options, Long installationId, TypeInstallations typeInstallations, String subtype, float x, float y) throws IOException {
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
            byte[] graphFirst = GraphCreated.createGraph(pointPressure, "Pressure", countPumps,x,y,true,false);
            byte[] graphSecond = GraphCreated.createGraph(pointPowers, "Power", countPumps,x,y,false,false);
            byte[] graphThird = GraphCreated.createGraph(pointNPSH, "NPSH", countPumps,x,y,false,true);

            Paragraph line1 = new Paragraph("Код установки: " + installations.getName())
                    .setFont(font)
                    .setFontSize(16)
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);

            Paragraph line2 = new Paragraph("Код комплектации: "+options.createCode(installations))
                    .setFont(font)
                    .setFontSize(14)
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);

            // Устанавливаем позиции для строк
            line1.setFixedPosition(1,10,810, 800); // Позиция (x, y) для первой строки
            line2.setFixedPosition(1, 10,790,800 ); // Позиция (x, y) для второй строки

            // Добавляем строки в документ
            document.add(line1);
            document.add(line2);

        /*BufferedImage imageFirst = ImageIO.read(new ByteArrayInputStream(graphFirst));
        BufferedImage imageSecond = ImageIO.read(new ByteArrayInputStream(graphSecond));
        BufferedImage imageThird = ImageIO.read(new ByteArrayInputStream(graphThird));*/

//            Image pdfImageFirst = new Image(ImageDataFactory.create(graphFirst));
//            Image pdfImageSecond = new Image(ImageDataFactory.create(graphSecond));
//            Image pdfImageThird = new Image(ImageDataFactory.create(graphThird));

            Image image = new Image(ImageDataFactory.create(GraphCreated.createCombinedGraph(pump.getPointsPressure(),pump.getPointsPower(),pump.getPointsNPSH(),installations.getCountMainPumps(),x,y)));
            image.setFixedPosition(1,10, 30);
            document.add(image);

//            pdfImageFirst.setFixedPosition(10, 530);
//            pdfImageSecond.setFixedPosition(10, 280);
//            pdfImageThird.setFixedPosition(10, 30);
//
//            document.add(pdfImageFirst);
//            document.add(pdfImageSecond);
//            document.add(pdfImageThird);




            Table infoTable = createInfoTable(font);
            infoTable.setFixedPosition(1,275,457,250);
            document.add(infoTable);

            /*Table pumpTable = createPumpInfoTable(font);
            pumpTable.setFixedPosition(2,50, 50, 500);
            document.add(pumpTable);*/

            /*Table materialTable = createMaterialTable(font);
            materialTable.setFixedPosition(2,50, 0, 500);
            document.add(materialTable);*/

            Image imageFirst = new Image(ImageDataFactory.create(installations.getDrawingsPath().get(0)));
            Image imageSecond = new Image(ImageDataFactory.create(installations.getDrawingsPath().get(1)));
            Image imageThird = new Image(ImageDataFactory.create(installations.getDrawingsPath().get(2)));

            float availableWidth = (document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin()) / 2;
            float topHeight = 200;
            float bottomHeight = 250;

            imageFirst.scaleToFit(availableWidth, topHeight);
            imageSecond.scaleToFit(availableWidth, topHeight);
            imageThird.scaleToFit(document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin(), bottomHeight);

            imageFirst.setFixedPosition(2, document.getLeftMargin(), 400);
            imageSecond.setFixedPosition(2, document.getLeftMargin() + availableWidth, 400);
            imageThird.setFixedPosition(2, document.getLeftMargin(), 100);

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
        Table table = new Table(4);
        Cell headerCell = new Cell(1, 4)
                .add(new Paragraph("Информация о установке"))
                .setBold()
                .setFont(font)
                .setFontSize(16)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.setFont(font);
        table.setFontSize(12);
        table.addHeaderCell(headerCell);
        table.setFont(font).setFontSize(14);

        table.addCell(new Cell(1,2).add(new Paragraph("Название насоса")));
        table.addCell(new Cell(1,2).add(new Paragraph(pump.getName())));

        table.addCell(new Cell(1,2).add(new Paragraph("Тип Установки")));
        table.addCell(new Cell(1,2).add(new Paragraph(installations.getTypeInstallations().getTranslation())));


        table.addCell(new Cell(1,2).add(new Paragraph("Подтип Установки")));

        switch (installations.getClass().getSimpleName()){
            case "GMInstallation":
                table.addCell(new Cell(1,2).add(new Paragraph( SubtypeForGm.valueOf(installations.getSubtype().toString()).getTranslation())));
                break;
            case "HozPitInstallation"  :
                table.addCell(new Cell(1,2).add(new Paragraph( HozPitSubtypes.valueOf(installations.getSubtype().toString()).getTranslation())));
                break;
            case "PNSInstallationAFEIJP" :
                table.addCell(new Cell(1,2).add(new Paragraph( PNSSubtypes.valueOf(installations.getSubtype().toString()).getTranslation())));
                break;
            case "PNSInstallationERW":
                table.addCell(new Cell(1,2).add(new Paragraph( PNSSubtypes.valueOf(installations.getSubtype().toString()).getTranslation())));
        }


//        table.addCell("Количество насосов");
//        table.addCell(String.valueOf(installations.getCountMainPumps()+installations.getCountSparePumps()));
//        table.addCell("Количество основных насосов");
//        table.addCell(String.valueOf(installations.getCountMainPumps()));
//        table.addCell("Количество резервных насосов");
//        table.addCell(String.valueOf(installations.getCountSparePumps()));

        table.addCell(new Cell().add(new Paragraph("Количество основных насосов")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(installations.getCountMainPumps()))));
        table.addCell(new Cell().add(new Paragraph("Количество резервных насосов")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(installations.getCountSparePumps()))));

        if(installations.getClass().getSimpleName()=="GMInstallation"){
            GMInstallation installation = (GMInstallation) installations;
            table.addCell("Тип жидкости");
            table.addCell(String.valueOf(installations.getCoolantType().getTranslation()));
            table.addCell("Концентрация");
            if(installation.getConcentration() != null){
                table.addCell(String.valueOf(installation.getConcentration()));
            }else {
                table.addCell("Концентрация отсутствует");
            }
        }else {
            table.addCell("Тип жидкости");
            table.addCell(String.valueOf(installations.getCoolantType().getTranslation()));
            table.addCell("Концентрация");
            table.addCell("Концентрация отсутствует");
        }


        table.addCell(new Cell(1,2).add(new Paragraph("Температура")));
        table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(installations.getTemperature()))));

        table.addCell(new Cell(1,2).add(new Paragraph("Тип управления")));
        table.addCell(new Cell(1,2).add(new Paragraph(String.valueOf(installations.getControlType().toString()))));

        table.addCell("Подача");
        table.addCell(String.valueOf(installations.getFlowRate()));
        table.addCell("Напор");
        table.addCell(String.valueOf(installations.getPressure()));

        switch (installations.getClass().getSimpleName()){
            case "HozPitInstallation"  :
                HozPitInstallation hpInstallation = (HozPitInstallation) installations;
                table.addCell(new Cell(1,2).add(new Paragraph("Вид насоса")));
                table.addCell(new Cell(1,2).add(new Paragraph(hpInstallation.getPumpType().toString())));
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
                table.addCell(new Cell(1,2).add(new Paragraph("Вид насоса")));
                table.addCell(new Cell(1,2).add(new Paragraph(pnsInstallationErw.getPumpType().toString())));
            default:
                break;
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
