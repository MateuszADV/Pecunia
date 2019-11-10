package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order_item_sequence")
    private Long id;
    @Column(name = "note_id")
    private Long noteId;
    @Column(name = "country_en")
    private String countryEn;
    private String cod;
    private Double denomination;
    @Column(name = "currency_name")
    private String currencyName;
    @Column(name = "note_date")
    private String noteDate;
    @Column(name = "price_buy")
    private Double priceBuy;
    @Column(name = "price_sell")
    private Double priceSell;
    @Column(name = "price_sell_final")
    private Double priceSellFinal;
    private Integer quantity;
    private String making;
    private String description;
    @Column(name = "avers_path")
    private String aversPath;
    @Column(name = "reverse_path")
    private String reversePath;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
