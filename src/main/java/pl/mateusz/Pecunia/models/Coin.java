package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Data
@ToString
@Entity
@Table(name = "coins")
public class Coin extends Many {

    public Coin() {
    }

    public Coin(Date dateBuyNote, String nameCurrency, Integer signatureCode, Double priceBuy, Double priceSell, Integer quantity, String status, String statusSell, String description, String imgType, String aversPath, String reversePath, Double denomination, String coinDate, String quality, String series, String composition, String bought, Double diameter, Double thickness, Double weight, Currency currency) {
        super(dateBuyNote, nameCurrency, signatureCode, priceBuy, priceSell, quantity, status, statusSell, description, imgType, aversPath, reversePath);
        this.denomination = denomination;
        this.coinDate = coinDate;
        this.quality = quality;
        this.series = series;
        this.composition = composition;
        this.bought = bought;
        this.diameter = diameter;
        this.thickness = thickness;
        this.weight = weight;
        this.currency = currency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "coins_sequence")
    private Long id;
    private Double denomination;     //nominał
    @Column(name = "coin_date")
    private String coinDate;        //rok monety
    private String quality;         // stan monety
    private String series;          // lata bicia monetu
    private String composition;    // skład monety
    private String bought;          // miejsce zakupu

    private Double diameter;         //średnica monety w mm
    private Double thickness;       // grubość monety w mm
    private Double weight;          // waga monety w gramach

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;


}
