package pl.mateusz.Pecunia.models.forms.enums;

import lombok.Getter;

@Getter
public enum GoldApiCodeEnum {
    XAU("Gold", "Złoto"),
    XAG("Silver", "Srebro"),
    XPT("Platinum", "Platyna"),
    XPD("Palladium", "Palad");

    private String en;
    private String pl;

    GoldApiCodeEnum(String en, String pl) {
        this.en = en;
        this.pl = pl;
    }
}
