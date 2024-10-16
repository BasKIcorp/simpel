package org.simpel.pumpingUnits.model.enums.subtypes;

public enum SubtypeForGm {
    FREQUENCY_CONTROLLED("Управление частотное выходному давлению", "1I"),
    RELAY_CONTROL("Управление релейное выходному давлению", "1R"),
    PRESSURE_DROP_FREQUENCY_CONTROLLED("Управление частотное по перепаду давления", "2I"),
    PRESSURE_DROP_RELAY_CONTROL("Управление релейное по перепаду давления", "2R");
    private final String translation;
    private final String code;

    SubtypeForGm(String translation, String code) {
        this.translation = translation;
        this.code = code;
    }

    public String getTranslation() {
        return translation;
    }

    public String getCode() {
        return code;
    }
}
