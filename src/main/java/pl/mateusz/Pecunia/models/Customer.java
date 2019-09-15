package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_sequence")
    private Long id;
    @Column(name = "unique_id")
    private String uniqueId;
    private String name;
    private String lastname;
    private String city;
    @Column(name = "zip_code")
    private String zipCode;
    private String street;
    private String number;
    private String email;
    private String nick;
    private String phone;
    private String descryption;
}
