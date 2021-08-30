package kr.co.elasticworks.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
	private String userId;
	private String userPwd;
	
	public LoginRequest(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }
}