package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum ImgTypeEnum {
    SCAN("skan", "scan"),
    FOTO("foto", "foto"),
    WWW("www", "www"),
    LOC("LOC", "LOC");

    private String namePl;
    private String nameEn;

    ImgTypeEnum(String namePl, String nameEn) {
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
