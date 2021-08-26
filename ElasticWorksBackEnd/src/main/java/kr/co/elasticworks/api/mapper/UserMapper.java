package kr.co.elasticworks.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.elasticworks.api.model.UserVO;

@Mapper
public interface UserMapper {
	public UserVO selectOneUser(String userId);

	public int updateUser(UserVO User);
	
	int insertUser(UserVO UserVO);
	
	UserVO findByUserId(@Param("userId") String id);

	int userRoleSave(@Param("userNo") int userNo, @Param("roleNo") int roleNo);

	int findUserNo(@Param("userId") String id);

	int findRoleNo(@Param("roleName") String roleName);
	
	String findByUserPwd(String id);
}
