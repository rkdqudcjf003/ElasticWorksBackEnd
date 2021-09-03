
package kr.co.elasticworks.api.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;


@Data
public class User implements UserDetails {
	private int idx; // 회원 pk
	private String id;// varchar(45) NOT NULL,
	private String pwd;// varchar(45) NOT NULL,
	private String realName; // varchar(20) NOT NULL,
	private String nickName; // varchar(20) NOT NULL,
	private String phoneNumber;// ` varchar(45) NOT NULL,
	private String emailId;// ` varchar(45) NOT NULL,
	private String emailDomain;// varchar(45) NOT NULL,
	private String addressPre;// varchar(45) DEFAULT NULL,
	private String address;// ` varchar(45) DEFAULT NULL,
	private String roleName;

	public List<String> getRoleList() {
		if (this.roleName.length() > 0 ) {
			return Arrays.asList(this.roleName.split(","));
		}
		return new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "UserVO [userId=" + id + ", userPwd=" + pwd + ", userName=" + realName + ", roleName="
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
		return this.pwd;
	}

	@Override
	public String getUsername() {
		return this.id;
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
