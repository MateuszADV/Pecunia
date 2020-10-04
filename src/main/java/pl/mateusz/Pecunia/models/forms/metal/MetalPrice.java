package pl.mateusz.Pecunia.models.forms.metal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder()
public class MetalPrice {
    private Long Id;
    private String dataSetCode;
    private String dataBaseCode;
    private String name;
    private String description;
    private String refreshedAt;
    private String newestAvailableDate;
    private String oldestAvailableDate;
    List<String> columnNames = new ArrayList<>();
    private String frequency;
    private String type;
    private Boolean premium;
    private Integer limit;
    private Boolean transform;
    private Boolean columnIndex;
    private String startDate;
    private String endDate;
    private List<String> date = new ArrayList<>();
    private Boolean collapse;
    private Boolean order;
    private Integer dataBaseId;
}
