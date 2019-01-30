package pl.mateusz.Pecunia.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    private Long id;
    @NotEmpty(message = "Pole nie może byc puste")
    @Size(min = 4, message = "Za krótka nazwa")
    private String continent;
    @NotEmpty(message = "Pole nie może byc puste")
    private String countryEn;
    @NotEmpty(message = "Pole nie może byc puste")
    private String countryPl;
    @NotEmpty(message = "Pole nie może byc puste")
    private String capitalCity;
    @NotEmpty(message = "Pole nie może byc puste")
    @Size(min = 2, max = 2, message = "pole musi zawierać dwie litery kodu")
    private String alfa2;
    @NotEmpty(message = "Pole nie może byc puste")
    @Size(min = 3, max = 3, message = "pole musi zawierać trzy litery kodu")
    private String alfa3;
    @NotEmpty(message = "Pole nie może byc puste")
    @Size(min = 3, max = 3, message = "pole musi zawierać trzy cyfrowy kod")
    private String numericCode;
    @NotEmpty(message = "Pole nie może byc puste")
    @Size(min = 13, max = 13, message = "ISO 3166-2:FK")
    private String isoCode;
}
