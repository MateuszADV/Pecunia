package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum ContinentEnum {
    EUROPE("Europa"),
    AFRICA("Afryka"),
    ASIA("Azja"),
    AUSTRALIA_OCEANIA("Australia i Oceania"),
    NORTH_AMERICA("Ameryka Północna"),
    SOUTH_AMERICA("Ameryka Południowa"),
    ANTARCTICA("Antarktyda");

    private String namePl;

    ContinentEnum(String namePl) {
        this.namePl = namePl;
    }

    public String getNamePl() {
        return namePl;
    }
}
