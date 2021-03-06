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
		accessToken.setHttpOnly(false);
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
		if (cookies == null)
			return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName))
				return cookie;
		}
		return null;
	}

//	public Cookie setCookie(HttpServletRequest req, String reqLogoutCookie) {
//		final Cookie[] cookies = req.getCookies();
//		if (cookies == null)
//			return null;
//		for (Cookie cookie : cookies) {
//			if (cookie.getName().equals(reqLogoutCookie)) {
//				
//				cookie.setHttpOnly(true);
//				cookie.setMaxAge(-1);
//				cookie.setPath("/");
//				cookie.setValue("");
//				
//				return cookie;
//			}
//		}
//		return null;
//	}
}
