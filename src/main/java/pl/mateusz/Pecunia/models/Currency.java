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
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "currency_sequence")
    private Long id;
    private String cod;
    @Column(name = "currency")
    private String nameCurrency;
    private String change;
    private int active;
    @Column(name = "data_exchange")
    private String dataExchange;
    @Column(name = "cod_old")
    private String codOld;
    @Column(name = "currency_old")
    private String nameCurrencyOld;
    @Column(name = "change_old")
    private String changeOld;
    private String converter;
    @Column(name = "until_when_exchange")
    private String untilWhenExchange;
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
