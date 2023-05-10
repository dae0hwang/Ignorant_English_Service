package hello.api.enumforentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlarmType {
    SUBSCRIBE("SUBSCRIBE"),
    UPDATE("UPDATE");
    private String StringCheck;
}
