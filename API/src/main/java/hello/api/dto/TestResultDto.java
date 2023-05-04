package hello.api.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {

    private Long userId;
    private Long sentenceId;
    private String check;
    private LocalTime testTime;
}
