package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum WeightEnum {
    OUNCE("uncja", "ounce", 31.1034768);

    private String namePl;
    private String nameEn;
    private Double weight;

    WeightEnum(String namePl, String nameEn, Double weight) {
        this.namePl = namePl;
        this.nameEn = nameEn;
        this.weight = weight;
    }
}
