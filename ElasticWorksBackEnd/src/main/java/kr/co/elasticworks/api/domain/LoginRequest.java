package kr.co.elasticworks.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
	private String id;
	private String pwd;
	
	public LoginRequest(String userId, String userPwd) {
        this.id = userId;
        this.pwd = userPwd;
    }
}