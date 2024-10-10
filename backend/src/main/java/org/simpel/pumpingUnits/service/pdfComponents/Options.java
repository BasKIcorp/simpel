package org.simpel.pumpingUnits.service.pdfComponents;

import org.simpel.pumpingUnits.model.installation.ParentInstallations;

import java.util.HashMap;

public class Options {
    private String execution;
    private boolean vibrationMounts;
    private String collector;
    private String flangesOrGrooveLock;
    private String filter;
    private Integer expansionTank;
    private String bufferTank;
    private Integer bufferTankSize;
    private String bufferTankType;
    private Float fuse;
    private boolean airVent;
    private String makeUpMamOrPap;
    private Integer pressureMamOrPap;
    private Integer volumeMamOrPap;

    private static final HashMap<String, String> executionMap = new HashMap<>();
    private static final HashMap<String, String> collectorMap = new HashMap<>();
    private static final HashMap<String, String> flangesOrGrooveLockMap = new HashMap<>();
    private static final HashMap<String, String> filterMap = new HashMap<>();
    private static final HashMap<String, String> bufferTankMap = new HashMap<>();
    private static final HashMap<String, String> bufferTankTypeMap = new HashMap<>();
    private static final HashMap<String, String> makeUpMamOrPapMap = new HashMap<>();

    static {
        executionMap.put("исполнение стандартное", "S0");
        executionMap.put("исполнение в уличном кожухе", "S1");
        executionMap.put("исполнение в уличном кожухе с обогревом", "S2");
        executionMap.put("исполнение ариктическое", "S3");

        collectorMap.put("AISI304", "-A4");
        collectorMap.put("AISI316", "-A6");
        collectorMap.put("Углеродистая сталь", "-C2");

        flangesOrGrooveLockMap.put("грувлок", "-V");
        flangesOrGrooveLockMap.put("фланец", "-F");
        flangesOrGrooveLockMap.put("фланец с вибровставкой", "-Fv");

        filterMap.put("MS", "-MS");
        filterMap.put("PS", "-PS");

        bufferTankMap.put("Буферный бак из нержавеющей стали", "/SST");
        bufferTankMap.put("Буферный бак из углеродистой стали", "/CST");

        bufferTankTypeMap.put("гооизонтальный", "H");
        bufferTankTypeMap.put("вертикальный", "V");

        makeUpMamOrPapMap.put("подпиточный клапан", "MuP");
        makeUpMamOrPapMap.put("подпиточный модуль", "MuM");
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

    public boolean isVibrationMounts() {
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

    public int getExpansionTank() {
        return expansionTank;
    }

    public String getBufferTank() {
        return bufferTank;
    }

    public int getBufferTankSize() {
        return bufferTankSize;
    }

    public String getBufferTankType() {
        return bufferTankType;
    }

    public float getFuse() {
        return fuse;
    }

    public boolean isAirVent() {
        return airVent;
    }

    public String getMakeUpMamOrPap() {
        return makeUpMamOrPap;
    }

    public int getPressureMamOrPap() {
        return pressureMamOrPap;
    }

    public int getVolumeMamOrPap() {
        return volumeMamOrPap;
    }

    private String createCodeForPns() {
        StringBuilder st = new StringBuilder();
        st.append("(");

        if (executionMap.containsKey(execution)) {
            st.append(executionMap.get(execution));
            if (executionMap.get(execution).equals("S1")){
                throw new NullPointerException("Неверный тип исполнения для этого типа установки" );
            }
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts) {
            st.append("-Lg");
        } else {
            st.append("-0");
        }

        if (collectorMap.containsKey(collector)) {
            if(collectorMap.get(collector).equals("-C2") || collectorMap.get(collector).equals("-A6")) {
                throw new NullPointerException("Неправильный коллектор для этого вида установок");
            }
            st.append(collectorMap.get(collector));
        } else {
            throw new NullPointerException("Неправильный коллектор");
        }

        if(collector.equals("сталь 20")){
            st.append("-CS");
        }

        if(flangesOrGrooveLock != null){
            st.append("-V");
        }
        else{
            st.append("-0");
        }

        if (filterMap.containsKey(filter)) {
            st.append(filterMap.get(filter));
        } else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (expansionTank != null) {
            st.append("/ET");
            st.append(expansionTank);
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
            st.append(executionMap.get(execution));
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts) {
            st.append("-Lg");
        } else {
            st.append("-0");
        }

        if (collectorMap.containsKey(collector)) {
            if(collectorMap.get(collector).equals("C2")) {
                throw new NullPointerException("Неправильный для этого вида установок");
            }
            st.append(collectorMap.get(collector));
        } else {
            throw new NullPointerException("Неправильный коллектор");
        }
        if(flangesOrGrooveLock != null){
            st.append("-V");
        }
        else{
            st.append("-0");
        }

        if (filterMap.containsKey(filter)) {
            st.append(filterMap.get(filter));
        } else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (expansionTank != null) {
            st.append("/ET");
            st.append(expansionTank);
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
            st.append(executionMap.get(execution));
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts) {
            st.append("-Lg");
        } else {
            st.append("-0");
        }

        if (collectorMap.containsKey(collector)) {
            st.append(collectorMap.get(collector));
        } else {
            throw new NullPointerException("Неправильный коллектор");
        }
        if (flangesOrGrooveLockMap.containsKey(flangesOrGrooveLock)){
            st.append(flangesOrGrooveLockMap.get(flangesOrGrooveLock));
        }
        else {
            throw new NullPointerException("Нет flangesOrGrooveLock");
        }

        if (filterMap.containsKey(filter)) {
            st.append(filterMap.get(filter));
        } else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (expansionTank != null) {
            st.append("/ET");
            st.append(expansionTank);
        } else {
            st.append("/0");
        }

        if (bufferTankMap.containsKey(bufferTank)) {
            st.append(bufferTankMap.get(bufferTank));
            if (bufferTankSize != null) {
                st.append(bufferTankSize);
            } else {
                throw new NullPointerException("Нет данных о объеме");
            }
            if (bufferTankTypeMap.containsKey(bufferTankType)) {
                st.append(bufferTankTypeMap.get(bufferTankType));
            } else {
                throw new NullPointerException("Ошибка в типе буферного бака");
            }
        } else {
            st.append("/SST0");
        }

        st.append(")");
        st.append("(");

        if (fuse != null) {
            st.append("SV");
            st.append(fuse);
        }
        else {
            st.append("0");
        }

        if (airVent){
            st.append("-AV");
        }
        else {
            st.append("-0");
        }

        st.append(")");
        st.append("(");

        if (makeUpMamOrPapMap.containsKey(makeUpMamOrPap)) {
            st.append(makeUpMamOrPapMap.get(makeUpMamOrPap));
            if (pressureMamOrPap != null) {
                st.append(pressureMamOrPap);
            }
            else {
                throw new NullPointerException("Нехватает давления мам пап");
            }
            if (volumeMamOrPap != null) {
                st.append("-");
                st.append(volumeMamOrPap);
            }
            else {
                throw new NullPointerException("Нехватает объема мам пап");
            }
        }
        else {
            st.append("0");
        }

        st.append(")");
        return st.toString();
    }


}