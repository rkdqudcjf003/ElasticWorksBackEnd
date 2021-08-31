package kr.co.elasticworks.api.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.elasticworks.api.domain.User;
import kr.co.elasticworks.api.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	private Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int insertUser(User user) {

		String password = user.getUserPwd();
		user.setUserPwd(bCryptPasswordEncoder.encode(password));
		int userInsertflag = userMapper.insertUser(user);
		if (userInsertflag > 0) {
			int userNo = userMapper.findUserNo(user.getUserId());
			int roleNo = userMapper.findRoleNo(user.getRoleName());
			userMapper.userRoleSave(userNo, roleNo);
			return 1;
		}
		return 0;
	}
	
	public User selectOneUser(String userId) {
		return userMapper.selectOneUser(userId);

	}

	public int updateUser(String userId, User userVo) {
		User user = new User();
		log.info("Update User - 수정전 데이터: " + user);
		user.setUserPwd(userVo.getUserPwd());
		user.setUserRealName(userVo.getUserRealName());
		user.setUserNickName(userVo.getUserNickName());
		user.setUserPhoneNumber(userVo.getUserPhoneNumber());
		user.setUserEmail1(userVo.getUserEmail1());
		user.setUserEmail2(userVo.getUserEmail2());
		user.setUserAddress1(userVo.getUserAddress1());
		user.setUserAddress2(userVo.getUserAddress2());
		log.info("Update User - 수정후 데이터: " + user);

		return userMapper.updateUser(user);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		System.out.println("========================== UserServiceImpl의 loadUserByUsername() ==========================");
		User user = userMapper.findByUserId(userId);
		log.info("USER_ID!! " + userId);
		log.info("USER_VO!! " + user);
		if (user == null) {
			throw new UsernameNotFoundException(userId + " : 사용자 존재하지 않음");
		}
		System.out.println("========================== UserServiceImpl의 loadUserByUsername() 종료 ======================");
		return user;

	}
}