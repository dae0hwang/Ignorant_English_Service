package hello.api.enumforentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grammar {

    NO("NO"),
    HAVEPP("HAVE PP"),
    IF("IF"),
    AS("AS");

    private final String stringGrammar;
}
