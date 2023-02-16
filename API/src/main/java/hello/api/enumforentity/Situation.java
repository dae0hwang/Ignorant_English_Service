package hello.api.enumforentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Situation {
    NO("NO"),
    BUSINESS("BUSINESS"),
    CAFE("CAFE");

    private final String stringSituation;

}
