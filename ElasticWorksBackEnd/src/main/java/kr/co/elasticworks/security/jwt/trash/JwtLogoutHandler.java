package kr.co.elasticworks.security.jwt.trash;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import kr.co.elasticworks.security.util.cookie.CookieUtil;


//@Service
public class JwtLogoutHandler{
//	implements LogoutHandler {

//	private Logger log = LogManager.getLogger(this.getClass());
//	@Autowired
//	private CookieUtil cookieUtil;
//	
//	@Override
//	public void logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		
//		System.out.println("========================================logoutHandler - auth null or not null========================================");
//		System.out.println(authentication);
//		System.out.println(auth);
//		Cookie setAccessCookie = cookieUtil.setCookie(req, "accessToken"); 
//		Cookie setRefreshCookie = cookieUtil.setCookie(req, "refreshToken");
//		log.info(setAccessCookie);
//		log.info(setRefreshCookie);
//		
//		res.addCookie(setAccessCookie);
//		res.addCookie(setRefreshCookie);
//		new SecurityContextLogoutHandler().logout(req, res, auth);
//		if (auth != null) {
//			System.out.println("============================================================================LogoutHandler============================================================================");
////			Cookie accessCookie = cookieUtil.getCookie(req, "accessToken");
////			Cookie refreshCookie = cookieUtil.getCookie(req, "refreshToken");
////			log.info(accessCookie);
////			log.info(refreshCookie);
//			
//			System.out.println("=====================================================================================================================================================================");
//		}
//	}

    
}
