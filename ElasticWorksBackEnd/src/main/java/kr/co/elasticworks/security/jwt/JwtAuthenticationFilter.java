package kr.co.elasticworks.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.elasticworks.api.model.LoginRequestDTO;
import kr.co.elasticworks.api.model.UserVO;
import kr.co.elasticworks.security.util.cookie.CookieUtil;
import kr.co.elasticworks.security.util.redis.RedisService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	private final AuthenticationManager authenticationManager;

	@Autowired
	RedisService redisService;

	

	public JwtAuthenticationFilter(AuthenticationManager authManager, RedisService redisService) {
		super();
        this.authenticationManager = authManager;
        this.redisService = redisService;

    }
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("===================================================================JWT AuthenticationFilter: 진입===================================================================");
		System.out.println("==================================================================attemptAuthentication() 함수 실행==================================================================");
		// request에 있는 username과 password를 파싱해서 자바 Object로 받기
		ObjectMapper om = new ObjectMapper();
		LoginRequestDTO loginRequestDto = null;

		try {
			loginRequestDto = om.readValue(request.getReader(), LoginRequestDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("LOGIN REQUEST DTO: " + loginRequestDto);

		// 유저네임패스워드 토큰 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequestDto.getUserId(), loginRequestDto.getUserPwd());

		setDetails(request, authenticationToken);
		System.out.println("JWT AuthenticationFilter: 토큰생성완료");
		
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		UserVO user = (UserVO) authentication.getPrincipal();
		
		log.info("AUTHENTICATION : " + user.getUsername());
		System.out.println("==================================================================attemptAuthentication() 함수 종료==================================================================");

		return authentication;
	}

	// JWT Token 생성해서 response에 담아주기
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("==================================================================successfulAuthentication() 함수 종료==================================================================");

		UserVO user = (UserVO) authResult.getPrincipal();
		log.info("PRINCIPAL DETAILS: " + user);

		JwtTokenUtil createToken = new JwtTokenUtil();
		System.out.println("Jwt Util 객체 생성");
		
		String accessJwt = createToken.generateAccessToken(user);
		String refreshJwt = createToken.generateRefreshToken(user);
		System.out.println("=================================================================================REDIS=================================================================================");
		
//		String Key = redisData.getRedisStoreKey();
		redisService.setDataExpire(refreshJwt, user.getUserId(), JwtProperties.REDIS_EXPIRATION_TIME);
		logger.info(redisService.getData(refreshJwt));
		System.out.println("=======================================================================================================================================================================");
		
		
		System.out.println("Cookie 생성 시작");
		CookieUtil cookieUtil = new CookieUtil();
		System.out.println("CookieUtil 객체생성");
		Cookie cookieAccess = cookieUtil.createAccessCookie(JwtProperties.ACCESS_TOKEN, accessJwt);
		res.addCookie(cookieAccess);
		Cookie cookieRefresh = cookieUtil.createRefreshCookie(JwtProperties.REFRESH_TOKEN, refreshJwt);
		res.addCookie(cookieRefresh);
		log.info(cookieAccess.getName() + ": " + cookieAccess.getValue());
		log.info(cookieRefresh.getName() + ": " + cookieRefresh.getValue());
		System.out.println("==================================================================successfulAuthentication() 함수 종료==================================================================");
	}
}
