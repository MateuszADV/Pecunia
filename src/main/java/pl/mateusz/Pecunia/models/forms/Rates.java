package pl.mateusz.Pecunia.models.forms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder()
public class Rates {
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("cod")
    private String cod;
    @JsonProperty("mid")
    private Double mid;
}
