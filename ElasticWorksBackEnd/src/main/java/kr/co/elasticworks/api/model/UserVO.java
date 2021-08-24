
package kr.co.elasticworks.api.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;


@Data

public class UserVO implements UserDetails {
	private int userNo; // 회원 pk
	private String userId;// varchar(45) NOT NULL,
	private String userPwd;// varchar(45) NOT NULL,
	private String userRealName; // varchar(20) NOT NULL,
	private String userNickName; // varchar(20) NOT NULL,
	private String userHp;// ` varchar(45) NOT NULL,
	private String userEmail1;// ` varchar(45) NOT NULL,
	private String userEmail2;// varchar(45) NOT NULL,
	private String userAddress1;// varchar(45) DEFAULT NULL,
	private String userAddress2;// ` varchar(45) DEFAULT NULL,
	private String roleName;

	public List<String> getRoleList() {
		if (this.roleName.length() > 0 ) {
			return Arrays.asList(this.roleName.split(","));
		}
		return new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userRealName + ", roleName="
				+ roleName + "]";
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
	        auth.add(new SimpleGrantedAuthority("ROLE_" + getRoleName()));
		return auth;
	}
	
	@Override
	public String getPassword() {
		return this.userPwd;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
