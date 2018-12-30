package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "country_sequence")
    private Long id;
    private String continent;
    @Column(name = "country_en")
    private String countryEn;
    @Column(name = "country_pl")
    private String countryPl;
    @Column(name = "capital_city")
    private String capitalCity;

    @OneToMany(mappedBy = "country", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Currency> currencies;
}
