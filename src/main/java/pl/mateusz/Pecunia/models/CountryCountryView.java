package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "v_currency_country")
public class CountryCountryView {

    @Id
    private Long id;
    private String continent;
    private String country_en;
    private String cod;
    private String currency;
    private String change;
    private int active;
}
