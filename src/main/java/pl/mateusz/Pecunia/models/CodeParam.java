package pl.mateusz.Pecunia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "code_param")
public class CodeParam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "code_sequence")
    private Long id;
    @Column(name = "web_name")
    private String webName;
    private String parameters;
}
