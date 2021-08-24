package kr.co.elasticworks.api.model;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {
	private String userId;
	private String userPwd;
	
	
	public LoginRequestDTO(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }
}