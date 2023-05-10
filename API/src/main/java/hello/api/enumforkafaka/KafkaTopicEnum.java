package hello.api.enumforkafaka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KafkaTopicEnum {

    CREATETABLE("CREATETABLE"),
    SUBSCRIBE("SUBSCRIBE"),
    ADDSENTECE("ADDSENTECE"),
    DELETESENTENCE("DELETESENTENCE"),
    CHECKALARM("CHECKALARM");

    private final String stringTopic;
}
