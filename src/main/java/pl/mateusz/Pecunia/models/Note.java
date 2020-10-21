//package pl.mateusz.Pecunia.models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.sql.Date;
//
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "notes")
//public class Note {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "notes_sequence")
//    private Long id;
//    private Double denomination;
//    @Column(name = "name_currency")
//    private String nameCurrency;
//    @Column(name = "note_date")
//    private String noteDate;
//    @Column(name = "signature_code")
//    private Integer signatureCode;
//    @Column(name = "price_buy")
//    private Double priceBuy;
//    @Column(name = "price_sell")
//    private Double priceSell;
//    private Integer quantity;
//    private String quality;
//    private String status;
//    private String description;
//    @Column(name = "img_type")
//    private String imgType;
//    @Column(name = "avers_path")
//    private String aversPath;
//    @Column(name = "reverse_path")
//    private String  reversePath;
//    private String series;
//    private String making;
//    @Column(name = "date_buy_note")
//    private Date dateBuyNote;
//    private String bought;
//    @Column(name = "status_sell")
//    private String statusSell;  //StatusSell odpowiada za to czy dany banknot został wystawiony na sprzedaż
//
//    @ManyToOne
//    @JoinColumn(name = "currency_id")
//    private Currency currency;
//}

package pl.mateusz.Pecunia.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "notes")
public class Note extends Many {


    public Note(Date dateBuyNote, String nameCurrency, Integer signatureCode, Double priceBuy, Double priceSell, Integer quantity, String status, String statusSell, String description, String imgType, String aversPath, String reversePath, Double denomination, String noteDate, String quality, String series, String making, String bought) {
        super(dateBuyNote, nameCurrency, signatureCode, priceBuy, priceSell, quantity, status, statusSell, description, imgType, aversPath, reversePath);
        this.denomination = denomination;
        this.noteDate = noteDate;
        this.quality = quality;
        this.series = series;
        this.making = making;
        this.bought = bought;
    }
    public Note() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "notes_sequence")
    private Long id;
    private Double denomination;
    @Column(name = "note_date")
    private String noteDate;
    private String quality;
    private String series;
    private String making;
    private String bought;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

}

