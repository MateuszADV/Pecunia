package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "v_country_currency")
public class CountryCurrencyView {


    @Id
    @Column(name = "country_id")
    private Long countryId;
    private String continent;
    @Column(name = "country_en")
    private String countryEn;
    @Column(name = "currency_id")
    private Long currencyId;
    private String cod;
    private String currency;
}
