package org.simpel.pumpingUnits.model.enums.subtypes;

public enum PNSSubtypes {
    ERW_SYSTEM("Внутренний Пожарный Водопровод","W"),
    AFEIJP("Автоматическая установка пожаротушения - Спринклерная с жокей насосом","S"),
    AFEIJP2("Автоматическая установка пожаротушения - Дренчерная без жокей насоса","D");

    private final String translation;
    private final String code;

    PNSSubtypes(String translation, String code) {
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
