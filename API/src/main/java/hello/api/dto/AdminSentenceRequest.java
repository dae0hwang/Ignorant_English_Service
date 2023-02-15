package hello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSentenceRequest {

    private String korean;
    private String english;
    private String grammar;
    private String situation;
}
