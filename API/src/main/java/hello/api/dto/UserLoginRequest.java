package hello.api.dto;

import lombok.Data;

//aa
@Data
public class UserLoginRequest {
	private String username;
	private String password;
}
