package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum PatternEnum {
    NOTE("Note", "banknot"),
    COIN("Coin", "monetę"),
    DEBENTURE("Debenture", "obligacje");

    private String name;
    private String namePl;

    PatternEnum(String name, String namePl) {
        this.name = name;
        this.namePl = namePl;
    }
}
