package kr.co.elasticworks.api.service;

import kr.co.elasticworks.api.model.UserVO;

public interface UserService {
	public UserVO loginUser(String userId, String password) throws Exception;

	public int updateUser(String userId, UserVO userVo) throws Exception;

	public int insertUser(UserVO userVo) throws Exception;

	public UserVO selectOneUser(String userId) throws Exception;
}
