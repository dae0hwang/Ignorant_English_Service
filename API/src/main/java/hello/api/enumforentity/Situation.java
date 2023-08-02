package hello.api.enumforentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Situation {
    NO("NO"),
    BUSINESS("BUSINESS"),
    REACTION("REACTION"),
    EVERYDAY("EVERYDAY"),
    MEETING("MEETING");

    private final String stringSituation;
}
