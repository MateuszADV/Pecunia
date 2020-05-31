package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "gold_rate")
public class GoldRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gold_sequence")
    private Long id;
    @Column(name = "data_rate")
    private Date dataRate;
    @Column(name = "price_for_gram")
    private Double priceForGram;
    @Column(name = "price_for_ounce")
    private Double priceForOunce;
    @Column(name = "price_for_ounce_on_usd")
    private Double priceForOunceOnUsd;
    private Double change;
}
