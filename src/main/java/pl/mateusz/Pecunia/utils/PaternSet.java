package pl.mateusz.Pecunia.utils;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PaternSet {

    private String patternSet = null;

    public void patternSet(String pattern) {
        patternSet = pattern;
    }

    @Bean
    public String patternGet() {
        return patternSet;
    }


}
