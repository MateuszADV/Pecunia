package pl.mateusz.Pecunia.models.forms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

public class CurrencyCode {
    @Setter
    private List<String> currencyCode;

    public List<String> getCurrencyCode() {
        List<String> codeUpperCase = new ArrayList<>();
        for (String s : this.currencyCode) {
            codeUpperCase.add(s.toUpperCase());
        }
        return codeUpperCase;
    }
}
