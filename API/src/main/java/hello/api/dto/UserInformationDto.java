package hello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInformationDto {
    private String username;
    private Long id;
    private String name;
    private boolean emailAuth;
}
