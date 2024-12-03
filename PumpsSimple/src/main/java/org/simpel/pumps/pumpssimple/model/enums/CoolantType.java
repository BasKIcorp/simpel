package org.simpel.pumps.pumpssimple.model.enums;

public enum CoolantType {
    WATER("Вода"),
    PROPYLENE_GLYCOL("Жижа номер уно"),
    ETHYLENE_GLYCOL("Жижа номер дес");
    private final String translation;

    CoolantType(String translation) {
        this.translation = translation;
    }
    public String getTranslation() {
        return translation;
    }
}
