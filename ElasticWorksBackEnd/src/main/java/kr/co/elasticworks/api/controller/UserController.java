package kr.co.elasticworks.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.elasticworks.api.domain.User;
import kr.co.elasticworks.api.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/read/{userId}")
	public User selectOneUser(@PathVariable("userId") String userId) throws Exception {
		return userService.selectOneUser(userId);
	}

	@PostMapping(value = "/signUp")
	public int insertUser(@RequestBody User userVo) throws Exception {
		log.info("Insert User Controller!");
		log.info(userVo);
		return userService.insertUser(userVo);
	}

	@PostMapping(value = "/{userId}")
	public int updateUser(@PathVariable("userId") String userId, User UserVo) throws Exception {
		return userService.updateUser(userId, UserVo);
	}

	@GetMapping(value = "/hello")
	public String welcomePage() {
		return "Hello. you have valid JWT (JSon Web Token)!";
	}

}
