package org.simpel.pumpingUnits.service.pdfComponents;

import org.simpel.pumpingUnits.model.installation.ParentInstallations;

import java.util.HashMap;

public class Options {
    private String execution;
    private String vibrationMounts;
    private String collector;
    private String flangesOrGrooveLock;
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

    private static final HashMap<String, String> executionMap = new HashMap<>();
    private static final HashMap<String, String> collectorMap = new HashMap<>();
    private static final HashMap<String, String> flangesOrGrooveLockMap = new HashMap<>();
    private static final HashMap<String, String> filterMap = new HashMap<>();
    private static final HashMap<String, String> bufferTankMap = new HashMap<>();
    private static final HashMap<String, String> bufferTankTypeMap = new HashMap<>();
    private static final HashMap<String, String> makeUpMamOrPapMap = new HashMap<>();

    //Ваниные гавняшки

    private static final HashMap<String, String> expansionTankMap = new HashMap<>();
    private static final HashMap<String, String> bufferTankSizeMap = new HashMap<>();
    private static final HashMap<String, String> fuseMap = new HashMap<>();


    static {
        executionMap.put("1", "S0");
        executionMap.put("2", "S1");
        executionMap.put("3", "S2");
        executionMap.put("4", "S3");

        collectorMap.put("1", "-A4");
        collectorMap.put("2", "-A6");
        collectorMap.put("3", "-C2");

        flangesOrGrooveLockMap.put("1", "-V");
        flangesOrGrooveLockMap.put("2", "-F");
        flangesOrGrooveLockMap.put("фланец с вибровставкой", "-Fv");

        filterMap.put("2", "-MS");
        filterMap.put("3", "-PS");

        bufferTankMap.put("1", "/SST");
        bufferTankMap.put("2", "/CST");

        bufferTankTypeMap.put("2", "H");
        bufferTankTypeMap.put("1", "V");

        makeUpMamOrPapMap.put("1", "MuP");
        makeUpMamOrPapMap.put("2", "MuM");

        //ваннины сыны

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
            st.append(executionMap.get(execution));
            if (executionMap.get(execution).equals("S1")){
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
            if(collectorMap.get(collector).equals("-C2") || collectorMap.get(collector).equals("-A6")) {
                throw new NullPointerException("Неправильный коллектор для этого вида установок");
            }
            st.append(collectorMap.get(collector));
        } else {
            throw new NullPointerException("Неправильный коллектор");
        }

        if(collector.equals("4")){
            st.append("-CS");
        }

        if(vibrationLock.equals("1")){
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
            st.append(executionMap.get(execution));
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts.equals("2")) {
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
        if(vibrationLock.equals("1")){
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
            st.append(executionMap.get(execution));
        } else {
            throw new NullPointerException("Неправильные исполнение");
        }

        if (vibrationMounts.equals("2")) {
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

        if (expansionTankMap.containsKey(expansionTank)) {
            st.append("/ET");
            st.append(expansionTankMap.get(expansionTank));
        } else {
            st.append("/0");
        }

        if (bufferTankMap.containsKey(bufferTank)) {
            st.append(bufferTankMap.get(bufferTank));
            if (bufferTankSizeMap.containsKey(bufferTankSize)) {
                st.append(bufferTankSizeMap.get(bufferTankSize));
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
            st.append(makeUpMamOrPapMap.get(makeUpMamOrPap));
            if (!pressureMamOrPap.equals("")) {
                st.append(pressureMamOrPap);
            }
            else {
                throw new NullPointerException("Нехватает давления мам пап");
            }
            if (!volumeMamOrPap.equals("")) {
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