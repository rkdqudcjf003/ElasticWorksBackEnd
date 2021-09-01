package kr.co.elasticworks.api.service;

import kr.co.elasticworks.api.domain.User;

public interface UserService {
	public int insertUser(User userVo) throws Exception;

	public int updateUser(String userId, User userVo) throws Exception;

	public User selectOneUser(String userId) throws Exception;
	
	public User selectAllUser() throws Exception;
		
}
