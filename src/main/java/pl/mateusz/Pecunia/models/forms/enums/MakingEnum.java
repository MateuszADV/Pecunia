package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum MakingEnum {
    PAPER("Papier", "Paper"),
    POLYMER("Polimer", "Polymer"),
    HYBRID("Hybryda", "Hybrid");

    private String namePl;
    private String nameEn;

    MakingEnum(String namePl, String nameEn) {
        this.namePl = namePl;
        this.nameEn = nameEn;
    }

    public String getNamePl() {
        return namePl;
    }

    public String getNameEn() {
        return nameEn;
    }
}
