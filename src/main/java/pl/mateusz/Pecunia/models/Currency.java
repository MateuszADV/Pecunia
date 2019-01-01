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
    @Column(name = "data_exchange")
    private String dataExchange;
    @Column(name = "currency_from")
    private String currencyFrom;
    private String converter;
    private int active;
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
