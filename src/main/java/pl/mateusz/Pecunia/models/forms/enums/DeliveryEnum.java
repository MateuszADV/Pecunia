package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum DeliveryEnum {
    ECONOMIC("List polecony ekonomiczny", "economic"),
    PRIORITY("List polecony Priorytet", "priority"),
    PERSONAL("Odbiór osobisty", "personal");

    private String namePL;
    private String nameDB;

    DeliveryEnum(String namePL, String nameDB) {
        this.namePL = namePL;
        this.nameDB = nameDB;
    }

    public String getNamePL() {
        return namePL;
    }

    public String getNameDB() {
        return nameDB;
    }
}
