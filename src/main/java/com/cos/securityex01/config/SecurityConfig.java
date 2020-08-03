package com.cos.securityex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //IOC 빈(bean = 객체) 을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션 (시큐리티 필터체인을 전체적으로 관리 할수있다.)
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled = true) //특정 주소 접근시 (컨트롤러 접근전에) 권한 및 인증을 미리 체크  
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //원하는것만 오버라이딩해서 관리가능
	
	@Bean // 메서드를 IOC 등록 
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //csrf-token 을 disable 시킨다.
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()  // authenticated = 인증을 물어봄
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //access = 권한을 물어봄 access 함수를 쓰는 이유는 or , and 사용이 가능하다. 
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/login") // '/user', '/admin' 을 login페이지로 연결되도록 한다.
			.loginProcessingUrl("/loginProc") // '/loginProc' 주소가 들어왔을때 Authentication Manager 타게 만들수 있음
			.defaultSuccessUrl("/");
	}
	
}
