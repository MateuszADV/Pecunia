package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteCountryViewDto {

    private Long countryId;
    private String continent;
    private String countryEn;
    private String alfa3;

}
