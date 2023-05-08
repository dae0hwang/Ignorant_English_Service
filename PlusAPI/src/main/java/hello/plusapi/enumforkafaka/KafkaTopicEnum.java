package hello.plusapi.enumforkafaka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KafkaTopicEnum {

    CREATETABLE(Constants.CREATETABLE),
    SUBSCRIBE(Constants.SUBSCRIBE),
    ADDSENTECE(Constants.ADDSENTECE),
    DELETESENTENCE(Constants.DELETESENTENCE),
    CHECKALARM(Constants.CHECKALARM);

    private final String stringTopic;

    public static class Constants {
        public static final String CREATETABLE = "CREATETABLE";
        public static final String SUBSCRIBE = "SUBSCRIBE";
        public static final String ADDSENTECE = "ADDSENTECE";
        public static final String DELETESENTENCE = "DELETESENTENCE";
        public static final String CHECKALARM = "CHECKALARM";

    }
}