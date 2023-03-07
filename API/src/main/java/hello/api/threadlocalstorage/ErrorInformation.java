package hello.api.threadlocalstorage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInformation {

    private String errorType;
    private String errorTitle;
    private String errorDetail;
}
