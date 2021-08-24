package kr.co.elasticworks.security.util.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.co.elasticworks.security.jwt.JwtProperties;


@Service
public class CookieUtil {
	public Cookie createAccessCookie(String cookiename, String value) {
		Cookie accessToken = new Cookie(cookiename, value);
//		accessToken.setDomain("localhost:4200");
		accessToken.setHttpOnly(true);
		accessToken.setMaxAge(JwtProperties.ACCOOKIE_EXPIRATION_TIME);
		accessToken.setPath("/");
		return accessToken;													
	}
	
	public Cookie createRefreshCookie(String cookiename, String value) {
		Cookie refreshToken = new Cookie(cookiename, value);
//		accessToken.setDomain("localhost:4200");
		refreshToken.setHttpOnly(true);
		refreshToken.setMaxAge(JwtProperties.RFCOOKIE_EXPIRATION_TIME);
		refreshToken.setPath("/");
		return refreshToken;													
	}
	
	
	public Cookie getCookie(HttpServletRequest req, String cookieName) {
		final Cookie[] cookies = req.getCookies();
		if(cookies == null) return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName))
				return cookie;
		}
		return null;
	}
}
