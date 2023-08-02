package hello.api.enumforentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grammar {

    NO("NO"),
    HAVEPP("HAVEPP"),
    IF("IF"),
    AS("AS"),
    WHICH("WHICH");

    private final String stringGrammar;
}
