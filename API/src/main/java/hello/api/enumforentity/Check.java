package hello.api.enumforentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Check {
    NO("NO"),
    WRONG("WRONG"),
    CORRECT("CORRECT");

    private final String stringCheck;
}
