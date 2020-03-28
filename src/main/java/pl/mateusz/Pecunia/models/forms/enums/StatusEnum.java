package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    KOLEKCJA("KOLEKCJA"),
    FOR_SELL("FOR SELL"),
    EXPIRED("FOR SELL, EXPIRED"),
    SOLD("SOLD"),
    NEW("NEW");

    private String name;

    StatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
