package hello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AdminTestCheckRequest {

    private Long userId;
    private Long sentenceId;
    private String check;
}
