package kr.co.elasticworks.security.config;



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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

import kr.co.elasticworks.security.jwt.JwtAuthenticationEntryPoint;
import kr.co.elasticworks.security.jwt.JwtAuthenticationFilter;
import kr.co.elasticworks.security.jwt.JwtRequestFilter;
import kr.co.elasticworks.security.jwt.trash.JwtAccessDeniedHandler;
import kr.co.elasticworks.security.util.redis.RedisService;
import kr.co.elasticworks.api.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final CorsFilter corsFilter;
	
    private final UserServiceImpl userDeatailsService;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    JwtRequestFilter jwtRequestFilter;
    
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
        		"/img/**"
        		);
    }

    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf().disable();// We don't need CSRF for JWT based authentication
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http
         	 .formLogin().disable()
     		 .httpBasic().disable();
    	 
         JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(), redisService);
     	 http.addFilter(corsFilter)
     	 	 .addFilter(jwtAuthenticationFilter) // AuthenticationManager
     	 	 .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //JwtAuthenticationFilter.class
     	 http
     	 // 인증없는 GEUST 접근 가능 URL
 			 .authorizeRequests()
			 .antMatchers(HttpMethod.POST,
					 "/home" ,
					 "/api/user/signUp",
					 "/login",
					 "/api/board/list").permitAll()
			 .and()
			 .authorizeRequests()
			 .antMatchers("/api/admin/**").permitAll()
// 			 .antMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")
 			 .and()
			 .authorizeRequests()
			 .antMatchers("/test/logoutResult").permitAll()
			 .and()
 			 .authorizeRequests()
			 .antMatchers("/test/redis").permitAll()
			 .and()
 			 .authorizeRequests()
			 .anyRequest().authenticated();
     	 
     	 http
     	 	 .logout()
     	 	 .logoutUrl("/logout")
     	 	 .logoutSuccessUrl("/login")
     	 	 .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
     	 	 .deleteCookies("accessToken", "refreshToken");
    }

}
