package kr.co.elasticworks.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;


@Slf4j
@Component
public class JwtAccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        httpServletResponse.setStatus(200);
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        Response response = new Response("error","접근가능한 권한을 가지고 있지 않습니다.",null);
//      
// 
//
//
//        PrintWriter out = httpServletResponse.getWriter();
//        String jsonResponse = objectMapper.writeValueAsString(response);
//        out.print(jsonResponse);
//
//    }

}
