package kr.co.elasticworks.security.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import kr.co.elasticworks.security.util.cookie.CookieUtil;


@Service
public class JwtLogoutHandler implements LogoutHandler {
	@Autowired
	private CookieUtil cookieUtil;
	
	@Override
	public void logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			System.out.println("============================================================================LogoutHandler============================================================================");
			Cookie accessCookie = cookieUtil.getCookie(req, "accessToken");
			Cookie refreshCookie = cookieUtil.getCookie(req, "refreshToken");
			Cookie setAccessCookie = cookieUtil.setCookie(accessCookie); 
			Cookie setRefreshCookie = cookieUtil.setCookie(refreshCookie);
			res.addCookie(setAccessCookie);
			res.addCookie(setRefreshCookie);
			new SecurityContextLogoutHandler().logout(req, res, auth);
			System.out.println("=====================================================================================================================================================================");
		}
	}

    
}
