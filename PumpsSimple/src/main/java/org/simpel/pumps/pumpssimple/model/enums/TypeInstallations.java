package org.simpel.pumps.pumpssimple.model.enums;

public enum TypeInstallations {
    GM("Гидромодуль"),
    HOZPIT("Хоз пит"),
    PNS("Насосная станция  пожаротушения");
    private final String translation;


    TypeInstallations(String translation) {
        this.translation = translation;
    }
    public String getTranslation() {
        return translation;
    }
}
