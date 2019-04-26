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
@Table(name = "v_note_info")
public class NoteInfoView {

    @Id
    @Column(name = "note_id")
    private Long noteId;
    private String continent;
    @Column(name = "country_en")
    private String countryEn;
    @Column(name = "country_id")
    private Long countryId;
    @Column(name = "currency_id")
    private Long currencyId;
    private String cod;
    private String currency;
    private Double denomination;
    @Column(name = "name_currency")
    private String nameCurrency;
    @Column(name = "note_date")
    private String noteDate;
    @Column(name = "signature_code")
    private Integer signatureCode;
    @Column(name = "price_buy")
    private Double priceBuy;
    @Column(name = "price_sell")
    private Double priceSell;
    private Integer quantity;
    private String quality;
    private String status;
    private String description;
    @Column(name = "avers_path")
    private String aversPath;
    @Column(name = "reverse_path")
    private String reversePath;

    private String series;
    private String making;
}
