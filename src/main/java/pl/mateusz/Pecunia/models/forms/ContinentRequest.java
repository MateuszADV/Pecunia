package pl.mateusz.Pecunia.models.forms;

import lombok.Data;

@Data
public class ContinentRequest {
    private Boolean europe;
    private Boolean africa;
    private Boolean asia;
    private Boolean australia;
    private Boolean northAmerica;
    private Boolean southAmerica;
    private Boolean antarctica;
}
