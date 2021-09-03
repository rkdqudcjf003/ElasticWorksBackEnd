package kr.co.elasticworks.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.elasticworks.api.domain.User;

@Mapper
public interface UserMapper {
	int insertUser(User UserVO);

	public User selectOneUser(String userId);
	
	public int updateUser(User User);
	
	User findByUserId(@Param("id") String id);

	int userRoleSave(@Param("userIdx") int userNo, @Param("roleIdx") int roleNo);

	int findUserNo(@Param("id") String id);

	int findRoleNo(@Param("roleName") String roleName);
	
	String findByUserPwd(String id);
	
	User selectAllUser(); 
}
