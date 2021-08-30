package kr.co.elasticworks.api.domain;

import lombok.Data;

@Data
public class JwtResponse {
	private String response;
    private String message;
    private Object data;

    public JwtResponse(String response, String message, Object data) {
        this.response = response;
        this.message = message;
        this.data = data;
    }
}
