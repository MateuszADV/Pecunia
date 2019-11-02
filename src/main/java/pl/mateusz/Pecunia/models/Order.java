package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order_sequence")
    private Long id;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "date_order")
    private LocalDate dateOrder;
    private Boolean active;
    @Column(name = "date_send")
    private LocalDate dateSend;
    @Column(name = "tracking_number")
    private String trackingNumber;
    @Column(name = "shipment_type")
    private String shipmentType;
    @Column(name = "shipping_costs")
    private Double shippingCosts;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
