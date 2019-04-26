package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "notes_sequence")
    private Long id;
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
    @Column(name = "img_type")
    private String imgType;
    @Column(name = "avers_path")
    private String aversPath;
    @Column(name = "reverse_path")
    private String  reversePath;
    private String series;
    private String making;


    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
}
