package org.simpel.pumpingUnits.model.enums.subtypes;

public enum HozPitSubtypes {
    FREQUENCY_CONTROLLED("Управление релейное выходному давлению","R"),
    RELAY_CONTROL("Управление частотное выходному давлению","I");
    private final String translation;
    private final String code;

    HozPitSubtypes(String translation, String code) {
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
