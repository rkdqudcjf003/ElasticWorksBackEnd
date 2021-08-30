package kr.co.elasticworks.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.elasticworks.api.domain.JwtResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		ObjectMapper objectMapper = new ObjectMapper();

		httpServletResponse.setStatus(200);
		httpServletResponse.setContentType("application/json;charset=utf-8");
		
		JwtResponse response = new JwtResponse("error", "로그인이 되지 않은 사용자입니다.", null);
		
		PrintWriter out = httpServletResponse.getWriter();
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		out.print(jsonResponse);
	}
}
