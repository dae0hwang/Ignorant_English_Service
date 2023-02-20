package hello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminSentenceDto {

    private Long id;
    private String korean;
    private String english;
    private String grammar;
    private String situation;
}
