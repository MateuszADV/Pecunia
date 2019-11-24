package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum StatusSellEnum {
    NULL(""),
    OLX("OLX"),
    OLX2("olx"),
    PERSONAL_PICKUPL("PERSONAL PICKUP"),
    SOLD("SOLD");

    private String name;

    StatusSellEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
