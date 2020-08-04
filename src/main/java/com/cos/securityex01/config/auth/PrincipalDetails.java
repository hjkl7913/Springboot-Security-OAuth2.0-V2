package com.cos.securityex01.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.securityex01.model.User;

import lombok.Data;

// Authentication 객체에 저장할 수 있는 유일한 타입
// 시큐리티가 관리하기위해 함수 명이 정해져있음
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{
	
	private User user;
	private Map<String, Object> attributes;
	
	public PrincipalDetails(User user) {
		super();
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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
	public boolean isCredentialsNonExpired() {  //비밀번호 완료 되었는지
		return true;
	}

	@Override
	public boolean isEnabled() { //계정 활성화 되있는지
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
	
		return authorities;
	}

	
	//리소스 서버로 부터 받는 회원정보
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return "제공자 ID";
	}


	
}
