package kr.co.elasticworks.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.elasticworks.api.model.UserVO;
import kr.co.elasticworks.api.service.UserService;
import kr.co.elasticworks.security.jwt.JwtAuthenticationFilter;
import kr.co.elasticworks.security.jwt.JwtTokenUtil;
import kr.co.elasticworks.security.util.cookie.CookieUtil;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    private CookieUtil cookieUtil;
	
//    @Autowired
//    private RedisService redisUtil;
    
//    @Autowired
//    private RedisTemplate<String, Object> redisUtil;
    
	@GetMapping(value = "/read/{userId}")
	public UserVO selectOneUser(@PathVariable("userId") String userId) throws Exception {
		return userService.selectOneUser(userId);
	}

	@PostMapping(value = "/insert")
	public int insertUer(@RequestBody UserVO userVo) throws Exception {
		return userService.insertUser(userVo);
	}

//	@PostMapping("/login")
//	public void login(@RequestBody LoginRequestDTO user, HttpServletRequest req, HttpServletResponse res) throws Exception {
////		String hello = "hello Cookie";
////		res.getHeaderNames("Set-Cookie", refreshToken)
//		ValueOperations<String, Object> vop = redisUtil.opsForValue();
//		vop.set("test", "test");
//	}

	@PostMapping(value = "/{userId}")
	public int updateUserr(@PathVariable("userId") String userId, UserVO UserVo) throws Exception {
		return userService.updateUser(userId, UserVo);
	}

	@GetMapping(value = "/hello")
	public String welcomePage() {
		return "Hello. you have valid JWT (JSon Web Token)!";
	}

}
