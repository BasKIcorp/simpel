package org.simpel.pumpingUnits.service.pdfComponents;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.simpel.pumpingUnits.model.installation.ParentInstallations;

import java.util.HashMap;

public class Options {
    private String execution;
    private String vibrationMounts;
    private String collector;
    private String flangesOrGrooveLock;

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public void setVibrationMounts(String vibrationMounts) {
        this.vibrationMounts = vibrationMounts;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public void setFlangesOrGrooveLock(String flangesOrGrooveLock) {
        this.flangesOrGrooveLock = flangesOrGrooveLock;
    }

    public String getVibrationLock() {
        return vibrationLock;
    }

    public void setVibrationLock(String vibrationLock) {
        this.vibrationLock = vibrationLock;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setExpansionTank(String expansionTank) {
        this.expansionTank = expansionTank;
    }

    public void setBufferTank(String bufferTank) {
        this.bufferTank = bufferTank;
    }

    public void setBufferTankSize(String bufferTankSize) {
        this.bufferTankSize = bufferTankSize;
    }

    public void setBufferTankType(String bufferTankType) {
        this.bufferTankType = bufferTankType;
    }

    public void setFuse(String fuse) {
        this.fuse = fuse;
    }

    public void setAirVent(String airVent) {
        this.airVent = airVent;
    }

    public void setMakeUpMamOrPap(String makeUpMamOrPap) {
        this.makeUpMamOrPap = makeUpMamOrPap;
    }

    public void setPressureMamOrPap(String pressureMamOrPap) {
        this.pressureMamOrPap = pressureMamOrPap;
    }

    public void setVolumeMamOrPap(String volumeMamOrPap) {
        this.volumeMamOrPap = volumeMamOrPap;
    }

    private String vibrationLock;
    private String filter;
    private String expansionTank;
    private String bufferTank;
    private String bufferTankSize;
    private String bufferTankType;
    private String fuse;
    private String airVent;
    private String makeUpMamOrPap;
    private String pressureMamOrPap;
    private String volumeMamOrPap;

    private static final HashMap<String, String[]> executionMap = new HashMap<>();
    private static final HashMap<String, String[]> collectorMap = new HashMap<>();
    private static final HashMap<String, String[]> flangesOrGrooveLockMap = new HashMap<>();
    private static final HashMap<String, String[]> filterMap = new HashMap<>();
    private static final HashMap<String, String[]> bufferTankMap = new HashMap<>();
    private static final HashMap<String, String[]> bufferTankTypeMap = new HashMap<>();
    private static final HashMap<String, String[]> makeUpMamOrPapMap = new HashMap<>();

    //Ваниные гавняшки

    private static final HashMap<String, String> expansionTankMap = new HashMap<>();
    private static final HashMap<String, String> bufferTankSizeMap = new HashMap<>();
    private static final HashMap<String, String[]> fuseMap = new HashMap<>();


    static {
        executionMap.put("1", new String[] {"S0", "Стандартное"});
        executionMap.put("2", new String[] {"S1", "В уличном кожухе"});
        executionMap.put("3", new String[] {"S2", "В уличном кожухе с обогревом"});
        executionMap.put("4", new String[] {"S3", "Ариктическое"});

        collectorMap.put("1", new String[] {"-A4", "AISI304"});
        collectorMap.put("2", new String[] {"-A6", "AISI316"});
        collectorMap.put("3", new String[]{ "-C2", "Углеродистая сталь"});
        collectorMap.put("4", new String[]{ "-CS", "Сталь 20"});
    // new String[]{"", ""}
        flangesOrGrooveLockMap.put("1", new String[]{"-V", "Грувлок"});
        flangesOrGrooveLockMap.put("2", new String[]{"-F", "Фланец"});
        flangesOrGrooveLockMap.put("фланец с вибровставкой", new String[]{"-Fv", "Фланец с вибровставкой"});

        filterMap.put("2", new String[]{"-MS", "Один на коллектор"});
        filterMap.put("3", new String[]{"-PS", "На каждом насосе"});

        bufferTankMap.put("1",  new String[]{"/SST", "Нержавеющая сталь"});
        bufferTankMap.put("2",  new String[]{"/CST", "Углеродистая сталь"});

        bufferTankTypeMap.put("2",  new String[]{"H", "Горизонтальный"});
        bufferTankTypeMap.put("1",  new String[]{"V", "Вертикальный"});

        makeUpMamOrPapMap.put("1",  new String[]{"MuP", "Подпиточный клапан"});
        makeUpMamOrPapMap.put("2",  new String[]{"MuM", "Подпиточный модуль"});

        //ванины сыны

        expansionTankMap.put("1", "8");
        expansionTankMap.put("2", "12");
        expansionTankMap.put("3", "18");
        expansionTankMap.put("4", "8");
        expansionTankMap.put("5", "8");
        expansionTankMap.put("6", "8");
        expansionTankMap.put("7", "8");
        expansionTankMap.put("8", "8");
        expansionTankMap.put("9", "8");
        expansionTankMap.put("10", "8");
        expansionTankMap.put("11", "8");
        expansionTankMap.put("12", "8");
        expansionTankMap.put("13", "8");
        expansionTankMap.put("14", "8");
        expansionTankMap.put("15", "8");
        expansionTankMap.put("16", "8");
        expansionTankMap.put("17", "8");
        expansionTankMap.put("18", "8");


        bufferTankSizeMap.put("1","200");
        bufferTankSizeMap.put("2","300");
        bufferTankSizeMap.put("3","500");
        bufferTankSizeMap.put("4","750");
        bufferTankSizeMap.put("5","1000");
        bufferTankSizeMap.put("6","1500");
        bufferTankSizeMap.put("7","2000");
        bufferTankSizeMap.put("8","2500");
        bufferTankSizeMap.put("9","3000");
        bufferTankSizeMap.put("10","3500");
        bufferTankSizeMap.put("11","4000");
        bufferTankSizeMap.put("12","50000");


    }

    public <T extends ParentInstallations> String createCode(T installations) {
        switch (installations.getClass().getSimpleName()) {
            case "GMInstallation":
                return createCodeForGM();
            case "HozPitInstallation":
                return createCodeForHozPit();
            case "PNSInstallationERW":
            case "PNSInstallationAFEIJP":
                return createCodeForPns();
            default:
                throw new NullPointerException("Что то не так с типами установки");
        }
    }

    public String getExecution() {
        return execution;
    }

    public String getVibrationMounts() {
        return vibrationMounts;
    }

    public String getCollector() {
        return collector;
    }

    public String getFlangesOrGrooveLock() {
        return flangesOrGrooveLock;
    }

    public String getFilter() {
        return filter;
    }

    public String getExpansionTank() {
        return expansionTank;
    }

    public String getBufferTank() {
        return bufferTank;
    }

    public String getBufferTankSize() {
        return bufferTankSize;
    }

    public String getBufferTankType() {
        return bufferTankType;
    }

    public String getFuse() {
        return fuse;
    }

    public String getAirVent() {
        return airVent;
    }

    public String getMakeUpMamOrPap() {
        return makeUpMamOrPap;
    }

    public String getPressureMamOrPap() {
        return pressureMamOrPap;
    }

    public String getVolumeMamOrPap() {
        return volumeMamOrPap;
    }

    private String createCodeForPns() {
        StringBuilder st = new StringBuilder();
        st.append("(");

        if (executionMap.containsKey(execution)) {
            st.append(executionMap.get(execution)[0]);
            if (executionMap.get(execution)[0].equals("S1")){
                throw new NullPointerException("Неверный тип исполнения для этого типа установки" );
            }
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts.equals("2")) {
            st.append("-Lg");
        } else {
            st.append("-0");
        }

        if (collectorMap.containsKey(collector)) {
            if(collectorMap.get(collector)[0].equals("-C2") || collectorMap.get(collector)[0].equals("-A6")) {
                throw new NullPointerException("Неправильный коллектор для этого вида установок");
            }
            st.append(collectorMap.get(collector)[0]);
        }
        if(vibrationLock.equals("1")){
            st.append("-V");
        }
        else{
            st.append("-0");
        }

        if (filterMap.containsKey(filter)) {
            st.append(filterMap.get(filter)[0]);
        } else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (expansionTankMap.containsKey(expansionTank)) {
            st.append("/ET");
            st.append(expansionTankMap.get(expansionTank));
        } else {
            st.append("/0");
        }
        st.append(")");
        return st.toString();
    }

    private String createCodeForHozPit() {
        StringBuilder st = new StringBuilder();
        st.append("(");

        if (executionMap.containsKey(execution)) {
            st.append(executionMap.get(execution)[0]);
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts.equals("2")) {
            st.append("-Lg");
        } else {
            st.append("-0");
        }

        if (collectorMap.containsKey(collector)) {
            if(collectorMap.get(collector)[0].equals("C2")) {
                throw new NullPointerException("Неправильный для этого вида установок");
            }
            st.append(collectorMap.get(collector)[0]);
        } else {
            throw new NullPointerException("Неправильный коллектор");
        }
        if(vibrationLock.equals("1")){
            st.append("-V");
        }
        else{
            st.append("-0");
        }

        if (filterMap.containsKey(filter)) {
            st.append(filterMap.get(filter)[0]);
        } else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (expansionTankMap.containsKey(expansionTank)) {
            st.append("/ET");
            st.append(expansionTankMap.get(expansionTank));
        } else {
            st.append("/0");
        }
        st.append(")");
        return st.toString();
    }

    private String createCodeForGM() {
        StringBuilder st = new StringBuilder();
        st.append("(");

        if (executionMap.containsKey(execution)) {
            st.append(executionMap.get(execution)[0]);
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts.equals("2")) {
            st.append("-Lg");
        } else {
            st.append("-0");
        }

        if (collectorMap.containsKey(collector)) {
            if(collectorMap.get(collector)[0].equals("CS")) {
                throw new NullPointerException("Неправильный для этого вида установок");
            }
            st.append(collectorMap.get(collector)[0]);
        } else {
            throw new NullPointerException("Неправильный коллектор");
        }
        if (flangesOrGrooveLockMap.containsKey(flangesOrGrooveLock)){
            st.append(flangesOrGrooveLockMap.get(flangesOrGrooveLock)[0]);
        }
        else {
            throw new NullPointerException("Нет flangesOrGrooveLock");
        }

        if (filterMap.containsKey(filter)) {
            st.append(filterMap.get(filter)[0]);
        } else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (expansionTankMap.containsKey(expansionTank)) {
            st.append("/ET");
            st.append(expansionTankMap.get(expansionTank));
        } else {
            st.append("/0");
        }

        if (bufferTankMap.containsKey(bufferTank)) {
            st.append(bufferTankMap.get(bufferTank)[0]);
            if (bufferTankSizeMap.containsKey(bufferTankSize)) {
                st.append(bufferTankSizeMap.get(bufferTankSize));
            } else {
                throw new NullPointerException("Нет данных о объеме");
            }
            if (bufferTankTypeMap.containsKey(bufferTankType)) {
                st.append(bufferTankTypeMap.get(bufferTankType)[0]);
            } else {
                throw new NullPointerException("Ошибка в типе буферного бака");
            }
        } else {
            st.append("/SST0");
        }

        st.append(")");
        st.append("(");

        if (!fuse.equals("")) {
            st.append("SV");
            st.append(fuse);
        }
        else {
            st.append("0");
        }

        if (airVent.equals("2")){
            st.append("-AV");
        }
        else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (makeUpMamOrPapMap.containsKey(makeUpMamOrPap)) {
            st.append(makeUpMamOrPapMap.get(makeUpMamOrPap)[0]);
            if (!pressureMamOrPap.equals("")) {
                st.append(pressureMamOrPap);
            }
            else {
                throw new NullPointerException("Нехватает давления мам пап");
            }
            if (makeUpMamOrPap.equals("1")) {
                if (!volumeMamOrPap.equals("")) {
                    st.append("-");
                    st.append(volumeMamOrPap);
                } else {
                    throw new NullPointerException("Нехватает объема мам пап");
                }
            }
        }
        else {
            st.append("0");
        }

        st.append(")");
        return st.toString();
    }
    public Table createTable() {

        return new Table(3);
    }

    public <T extends ParentInstallations> Table createTable(T installations, PdfFont font) {
        switch (installations.getClass().getSimpleName()) {
            case "GMInstallation":
                return createTableForGm(font);
            case "HozPitInstallation":
                return createTableForHozPit(font);
            case "PNSInstallationERW":
            case "PNSInstallationAFEIJP":
                return createTableForPns(font);
            default:
                throw new NullPointerException("Что то не так с типами установки");
        }
    }

    private Table createTableForPns(PdfFont font) {
        // Определение ширины колонок: 10% для левой и по 18% для остальных 5 колонок
        float[] columnWidths = {1, 11, 11, 11, 11, 11};
        Table table = new Table(columnWidths);

        Cell headerCell = new Cell(1, 6)
                .add(new Paragraph("Дополнительные опции"))
                .setBold()
                .setFont(font)
                .setFontSize(16)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.addCell(headerCell);
        table.setFont(font).setFontSize(14);
        table.setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);

        // Добавляем строки в таблицу
        table.addCell(new Cell(1, 1).add(new Paragraph("Исполнение")));
        if (executionMap.containsKey(execution)) {
            if (executionMap.get(execution)[0].equals("S1")) {
                throw new NullPointerException("Неверный тип исполнения для этого типа установки");
            }
            table.addCell(new Cell(1, 5).add(new Paragraph(executionMap.get(execution)[1])));
        } else {
            throw new NullPointerException("нет исполнения в опциях");
        }

        table.addCell(new Cell(1, 1).add(new Paragraph("Виброопора")));
        table.addCell(new Cell(1, 5).add(new Paragraph(vibrationMounts.equals("2") ? "Есть" : "Нет")));

        table.addCell(new Cell(1, 1).add(new Paragraph("Материал коллектора")));
        if (collectorMap.containsKey(collector)) {
            if (collectorMap.get(collector)[0].equals("-C2") || collectorMap.get(collector)[0].equals("-A6")) {
                throw new NullPointerException("Неправильный коллектор для этого вида установок");
            }
            table.addCell(new Cell(1, 5).add(new Paragraph(collectorMap.get(collector)[1])));
        }

        table.addCell(new Cell(1, 1).add(new Paragraph("Виброкомпенсатор")));
        table.addCell(new Cell(1, 5).add(new Paragraph(vibrationLock.equals("1") ? "Есть" : "Нет")));

        table.addCell(new Cell(1, 1).add(new Paragraph("Расширительный бак")));
        if (expansionTankMap.containsKey(expansionTank)) {
            table.addCell(new Cell(1, 5).add(new Paragraph(expansionTankMap.get(expansionTank) + "л")));
        } else {
            table.addCell(new Cell(1, 5).add(new Paragraph("Нет")));
        }

        return table;
    }

    private Table createTableForHozPit(PdfFont font) {
        float[] columnWidths = {1000,1,1,1,1,1};
        Table table = new Table(columnWidths);
        Cell headerCell = new Cell(1, 6)
                .add(new Paragraph("Дополнительные опции"))
                .setBold()
                .setFont(font)
                .setFontSize(16)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.addCell(headerCell);
        table.setFont(font);
        table.setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.setFont(font).setFontSize(14);
        table.addCell(new Cell(1,5).add(new Paragraph("Исполнение")));
        if (executionMap.containsKey(execution)){
            table.addCell(
                    new Cell(1,5)
                            .add(
                                    new Paragraph(executionMap.get(execution)[1])
                            )
            );
        }
        else { throw new NullPointerException("нет исполнения в опциях");
        }

        table.addCell(new Cell(1,5).add(new Paragraph("Виброопора")));
        if(vibrationMounts.equals("2")){
            table.addCell(new Cell(1,5).add(new Paragraph("Есть")));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Нет")));
        }

        table.addCell(new Cell(1,5).add(new Paragraph("Материал коллектора")));
        if (collectorMap.containsKey(collector)) {
            if(collectorMap.get(collector)[0].equals("C2") || collectorMap.get(collector)[0].equals("CS")) {
                throw new NullPointerException("Неправильный для этого вида установок");
            }
            table.addCell(new Cell(1,5).add(new Paragraph(collectorMap.get(collector)[1])));
        }

        table.addCell(new Cell(1,5).add(new Paragraph("Виброкомпенсатор")));
        if(vibrationLock.equals("1")){
            table.addCell(new Cell(1,5).add(new Paragraph("Есть")));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Нет")));
        }

        table.addCell(new Cell(1,5).add(new Paragraph("Фильтр")));
        if(filterMap.containsKey(filter)){
            table.addCell(new Cell(1,5).add(new Paragraph(filterMap.get(filter)[1])));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Без фильтра")));
        }

        table.addCell(new Cell(1,5).add(new Paragraph("Расширительный бак")));
        if (expansionTankMap.containsKey(expansionTank)) {
            StringBuilder st = new StringBuilder();
            st.append(expansionTankMap.get(expansionTank));
            st.append("л");
            table.addCell(new Cell(1,5).add(new Paragraph(st.toString())));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Нет")));
        }

        return table;
    }

    private Table createTableForGm(PdfFont font) {
        float[] columnWidths = {1, 11, 11, 11, 11, 11};
        Table table = new Table(columnWidths);
        Cell headerCell = new Cell(1, 6)
                .add(new Paragraph("Дополнительные опции"))
                .setBold()
                .setFont(font)
                .setFontSize(12)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.addCell(headerCell);
        table.setFont(font);
        table.setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
        table.setFont(font).setFontSize(10);
        table.addCell(new Cell(1,5).add(new Paragraph("Исполнение")));
        if (executionMap.containsKey(execution)){
           table.addCell(
                   new Cell(1,5)
                           .add(
                                   new Paragraph(executionMap.get(execution)[1])
                           )
           );
        }
        else { throw new NullPointerException("нет исполнения в опциях");}
        table.addCell(new Cell(1,5).add(new Paragraph("Виброопора")));
        if(vibrationMounts.equals("2")){
            table.addCell(
                    new Cell(1,5)
                            .add(
                                    new Paragraph("Есть")
                            )
            );
        }
        else {
            table.addCell(
                    new Cell(1,5)
                            .add(
                                    new Paragraph("Нет")
                            )
            );
        }
        table.addCell(new Cell(1,5).add(new Paragraph("Материал коллектора")));
        table.addCell(new Cell(1,5).add(new Paragraph(collectorMap.get(collector)[1])));

        table.addCell(new Cell(1,5).add(new Paragraph("Не знаю про что речь 1")));
        table.addCell(new Cell(1,5).add(new Paragraph(flangesOrGrooveLockMap.get(flangesOrGrooveLock)[1])));

        table.addCell(new Cell(1,5).add(new Paragraph("Фильтр")));
        if(filterMap.containsKey(filter)){
            table.addCell(new Cell(1,5).add(new Paragraph(filterMap.get(filter)[1])));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Без фильтра")));
        }
        table.addCell(new Cell(1,5).add(new Paragraph("Расширительный бак")));
        if (expansionTankMap.containsKey(expansionTank)) {
            StringBuilder st = new StringBuilder();
            st.append(expansionTankMap.get(expansionTank));
            st.append("л");
            table.addCell(new Cell(1,5).add(new Paragraph(st.toString())));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Нет")));
        }

        table.addCell(new Paragraph("Буферный бак"));
        if(bufferTankMap.containsKey(bufferTank)){
            table.addCell(new Paragraph(bufferTankMap.get(bufferTank)[1]));
        }
        else {table.addCell(new Paragraph("Нет"));
            table.addCell(new Paragraph("Объем"));
            table.addCell(new Paragraph("Нет"));
            table.addCell(new Paragraph("Тип"));
            table.addCell(new Paragraph("Нет"));
        }
        table.addCell(new Paragraph("Объем"));
        table.addCell(new Paragraph(bufferTankSizeMap.get(bufferTankSize)));
        table.addCell(new Paragraph("Тип"));
        table.addCell(new Paragraph(bufferTankTypeMap.get(bufferTankType)[1]));

        table.addCell(new Cell(1,5).add(new Paragraph("Предохранитель")));
        if(!fuse.equals("")){
            table.addCell(new Cell(1,5).add(new Paragraph(fuse)));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Нет")));
        }

        table.addCell(new Cell(1,5).add(new Paragraph("Воздухоотводчик")));
        if(airVent.equals("2")){
            table.addCell(new Cell(1,5).add(new Paragraph("Есть")));
        }
        else {
            table.addCell(new Cell(1,5).add(new Paragraph("Нет")));
        }

        table.addCell(new Paragraph("Подпитка"));
        if(makeUpMamOrPapMap.containsKey(makeUpMamOrPap)){
            table.addCell(new Paragraph(makeUpMamOrPapMap.get(makeUpMamOrPap)[1]));
            table.addCell(new Paragraph("Давление"));
            table.addCell(new Paragraph(pressureMamOrPap));
        }
        else {
            table.addCell(new Paragraph("Давление"));
            table.addCell(new Paragraph("Нет"));
            table.addCell(new Paragraph("Объем емкости"));
            table.addCell(new Paragraph("Нет"));
        }
        if(makeUpMamOrPap.equals("1")){
            table.addCell(new Paragraph("Объем емкости"));
            table.addCell(new Paragraph(volumeMamOrPap));
        }
        else {
            table.addCell(new Paragraph("Объем емкости"));
            table.addCell(new Paragraph("Нет"));
        }
        return  table;
    }


}