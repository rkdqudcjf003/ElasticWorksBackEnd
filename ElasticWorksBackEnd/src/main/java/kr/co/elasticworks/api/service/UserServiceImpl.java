package kr.co.elasticworks.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.elasticworks.api.mapper.UserMapper;
import kr.co.elasticworks.api.model.UserVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserVO selectOneUser(String userId) {
		return userMapper.selectOneUser(userId);

	}

	public int updateUser(String userId, UserVO userVo) {

		UserVO User = new UserVO();
		User.setUserPwd(userVo.getUserPwd());
		User.setUserRealName(userVo.getUserRealName());
		User.setUserNickName(userVo.getUserNickName());
		User.setUserHp(userVo.getUserHp());
		User.setUserEmail1(userVo.getUserEmail1());
		User.setUserEmail2(userVo.getUserEmail2());
		User.setUserAddress1(userVo.getUserAddress1());
		User.setUserAddress2(userVo.getUserAddress2());

		return userMapper.updateUser(User);

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int insertUser(UserVO user) {

		String password = user.getUserPwd();
		user.setUserPwd(bCryptPasswordEncoder.encode(password));
		int flag = userMapper.insertUser(user);
		if (flag > 0) {

			int userNo = userMapper.findUserNo(user.getUserId());
			int roleNo = userMapper.findRoleNo(user.getRoleName());
			userMapper.userRoleSave(userNo, roleNo);
			return 1;
		}
		return 0;
	}

	@Override
	public UserVO loginUser(String userId, String userPassword) throws Exception {
		UserVO user = userMapper.findByUserId(userId);

		if (user == null)
			throw new Exception("멤버가 조회되지 않음");

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService의 loadUserByUsername()");

		UserVO user = userMapper.findByUserId(userId);
		System.out.println("USER_ID!! " + userId);
		System.out.println("USER_VO!! " + user);
		if (user == null) {
			throw new UsernameNotFoundException(userId + " : 사용자 존재하지 않음");
		}
		System.out.println("return");
		return user;

	}

}