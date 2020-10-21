package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "coins")
public class Coin extends Many {


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

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;


}
