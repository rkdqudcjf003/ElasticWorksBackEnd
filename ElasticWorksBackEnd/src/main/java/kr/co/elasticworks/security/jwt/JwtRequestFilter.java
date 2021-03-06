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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import kr.co.elasticworks.api.domain.User;
import kr.co.elasticworks.api.service.UserServiceImpl;
import kr.co.elasticworks.security.config.exception.CommonException;
import kr.co.elasticworks.security.util.cookie.CookieUtil;
import kr.co.elasticworks.security.util.redis.RedisService;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private UserServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CookieUtil cookieUtil;

	@Autowired
	RedisService redisService;

	protected void runVaildation(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws CommonException {
		System.out.println("=======================================================================JWT REQUEST FILTER START!!===================================================================");
		
		final Cookie cookieAccessToken = cookieUtil.getCookie(req, "accessToken");
		final Cookie cookieRefreshToken = cookieUtil.getCookie(req, "refreshToken");
		
		String username = null;
		String accessJwt = null;
		String refreshJwt = null;
		String refreshUserId = null;

		System.out.println("==============================================================JWT REQUEST FILTER - COOKIE ACCESS TOKEN===============================================================");
		
		try {
			if (cookieAccessToken != null) {
				accessJwt = cookieAccessToken.getValue();
				log.info("ACCESSTOKEN: " + accessJwt);
				username = jwtTokenUtil.getUsernameFromToken(accessJwt);
			} else {
				if (cookieRefreshToken != null) {
					refreshJwt = cookieRefreshToken.getValue();
					log.info("REFRESHTOKEN: " + refreshJwt);
				}
			}
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				System.out.println("jwt request filter - ??????????????????");
				if (jwtTokenUtil.validateToken(accessJwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					log.info(usernamePasswordAuthenticationToken);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}

		} catch (ExpiredJwtException e) {
			System.out.println("JWT ??????!");
		}
		System.out.println("=====================================================================================================================================================================");
		log.info(refreshJwt);
		System.out.println("==============================================================JWT REQUEST FILTER - COOKIE REFRESH TOKEN==============================================================");
		try {
			log.info("USER ID: " + refreshUserId);
			log.info("REFRESHTOKEN: " + refreshJwt);
			if (refreshJwt != null) {
				System.out.println("Refresh ?????? ?????? ??????");
				refreshUserId = redisService.getData(refreshJwt);
				log.info("USER ID: " + refreshUserId);
				log.info("REFRESHTOKEN: " + refreshJwt);
				
				if (refreshUserId.equals(jwtTokenUtil.getUsernameFromToken(refreshJwt))) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUserId);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
					User user = new User();
					user.setId(refreshUserId);
					// ????????? Access ?????? ??????
					String newToken = jwtTokenUtil.generateAccessToken(user);

					Cookie newAccessToken = cookieUtil.createAccessCookie("accessToken", newToken);

					log.info("USER: " + user);
					log.info("NEW TOKEN: " + newToken);
					log.info("NEW ACCESS TOKEN: " + newAccessToken);
					
					res.addCookie(newAccessToken);
					
					
					
				}
			}
		} catch (ExpiredJwtException e1) {

		}
		System.out.println("=====================================================================================================================================================================");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			this.runVaildation(req, res, filterChain);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		filterChain.doFilter(req, res);
	}
}
