package kr.co.elasticworks.security.config;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

import kr.co.elasticworks.security.jwt.CustomLogoutHandler;
import kr.co.elasticworks.security.jwt.JwtAccessDeniedHandler;
import kr.co.elasticworks.security.jwt.JwtAuthenticationEntryPoint;
import kr.co.elasticworks.security.jwt.JwtAuthenticationFilter;
import kr.co.elasticworks.security.jwt.JwtRequestFilter;
import kr.co.elasticworks.security.util.redis.RedisService;
import kr.co.elasticworks.api.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * WebSecurityConfig
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final CorsFilter corsFilter;
	
    private final UserServiceImpl userDeatailsService;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    
    @Autowired
    JwtRequestFilter jwtRequestFilter;
    
    @Autowired
    private CustomLogoutHandler logoutHandler;
    
    @Autowired
	RedisService redisService;
    
    @Autowired
    public WebSecurityConfig(UserServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
    		RedisService redisService, CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
		this.userDeatailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.redisService = redisService;
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDeatailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring()
        .antMatchers(
        		"/css/**",
        		"/js/**",
        		"/img/**",
        		"/v2/api-docs",
        		"/swagger-resources/**",
        		"/swagger-ui.html",
        		"/webjars/**",
        		"/swagger/**"
        		);
    }

    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf().disable();// We don't need CSRF for JWT based authentication
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http
         	 .formLogin().disable()
     		 .httpBasic().disable();
//     	 http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
     	 JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(), redisService);
//     	 JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(redisService);
     	 
     	 http.addFilter(corsFilter)
     	 	 .addFilter(jwtAuthenticationFilter) // AuthenticationManager
     	 	 .addFilterBefore(jwtRequestFilter, JwtAuthenticationFilter.class); //JwtAuthenticationFilter.class
     	 http
 			 .authorizeRequests()
 			 .antMatchers(HttpMethod.POST, "/api/user/**").hasAnyRole("USER", "ADMIN")
 			 .and()
			 .authorizeRequests()
 			 .antMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")
 			 .and()
			 .authorizeRequests()
 			 .antMatchers("/api/board/**").authenticated()
// 			 .hasAnyRole("USER", "ADMIN")
 			 .and()
			 .authorizeRequests()
			 .antMatchers(HttpMethod.POST, "/login").permitAll()
			 .and()
 			 .authorizeRequests()
			 .antMatchers("/test/logoutResult").permitAll()
			 .and()
 			 .authorizeRequests()
			 .antMatchers("/test/redis").permitAll()
			 .and()
 			 .authorizeRequests()
			 .anyRequest().permitAll();
     	 http
//     	 	 .formLogin()
//     	 	 .loginProcessingUrl("/api/user/sign-in")
//     	 	 .loginPage("/api/user/sign-in")
//     	 	 .defaultSuccessUrl("/home")
////     	 	 .successHandler(new CustomLoginSuccessHandler())
//     	 	 .and()
     	 	 .logout()
//     	 	 .logoutSuccessUrl("/test/logoutResult")
     	 	 .addLogoutHandler(logoutHandler)
     	 	 .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
     	 	 .deleteCookies("accessToken", "refreshToken");
    }

}
