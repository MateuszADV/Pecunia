package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class Many {

    @Column(name = "date_buy_note")
    private Date dateBuyNote;           //Data zakupu/ Data utworzenia seta banknotów
    @Column(name = "name_currency")
    private String nameCurrency;        //Nazwa waluty/ Nazwa Seta
    @Column(name = "signature_code")
    private Integer signatureCode;      // Sygnatura banknotu/ oznaczenie Seta (Opcjonalne narazie - 999 kod doseta określający różne banknoty)
    @Column(name = "price_buy")
    private Double priceBuy;            //Cena zakupu
    @Column(name = "price_sell")
    private Double priceSell;           //Cena Sprzedaży (sugerowana)
    private Integer quantity;           //ilość
    private String status;              // Status (kolekcja, na sprzedaż, sprzedane
    @Column(name = "status_sell")
    private String statusSell;          //StatusSell odpowiada za to czy dany banknot został wystawiony na sprzedaż
    private String description;         //Opis
    @Column(name = "img_type")
    private String imgType;             //Typ obrazka(skan, foto, ze strony)
    @Column(name = "avers_path")
    private String aversPath;
    @Column(name = "reverse_path")
    private String  reversePath;


    @Override
    public String toString() {
        return "Many{" +
                "dateBuyNote=" + dateBuyNote +
                ", nameCurrency='" + nameCurrency + '\'' +
                ", signatureCode=" + signatureCode +
                ", priceBuy=" + priceBuy +
                ", priceSell=" + priceSell +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", statusSell='" + statusSell + '\'' +
                ", description='" + description + '\'' +
                ", imgType='" + imgType + '\'' +
                ", aversPath='" + aversPath + '\'' +
                ", reversePath='" + reversePath + '\'' +
                '}';
    }
}
