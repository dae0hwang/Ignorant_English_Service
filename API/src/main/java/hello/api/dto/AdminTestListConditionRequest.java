package hello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTestListConditionRequest {

    private Long userId;
    private String grammar;
    private String situation;
    private String check;
}
