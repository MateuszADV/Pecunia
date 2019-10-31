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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order_sequence")
    private Long id;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "date_order")
    private Date dateOrder;
    @Column(name = "date_send")
    private Date dateSend;
    @Column(name = "tracking_number")
    private String trackingNumber;
    @Column(name = "shipment_type")
    private String shipmentType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
