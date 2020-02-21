package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum ContinentEnum {
    EUROPE("Europa", "Europa"),
    AFRICA("Afryka", "Afryka"),
    ASIA("Azja", "Azja"),
    AUSTRALIA_OCEANIA("Australia i Oceania", "Australia_i_Oceania"),
    NORTH_AMERICA("Ameryka Północna","Ameryka_Północna"),
    SOUTH_AMERICA("Ameryka Południowa","Ameryka_Południowa"),
    ANTARCTICA("Antarktyda", "Antarktyda"),
    SET_NOTES("Zestaw banknotów", "Zestaw");
//    AUTONOMOUS_TERRITORIES("Terytorium Autonomiczne", "Terytorium_Autonomiczne");

    private String namePl;
    private String webContinent;

    ContinentEnum(String namePl, String webContinent) {
        this.namePl = namePl;
        this.webContinent = webContinent;
    }

    public String getNamePl() {
        return namePl;
    }

    public String getWebContinent() {
        return webContinent;
    }
}
