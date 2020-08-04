package com.cos.securityex01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.securityex01.model.User;

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
public interface UserRepository extends JpaRepository<User, Integer>{
	
	//Jpa Naming 전략
	// SELECT * FROM user WHERE username = ?1
	User findByUsername(String username); //함수이름에 맞게 쿼리를 만들어냄
	
	//@Query(value = "select * from user where email = ?1", nativeQuery = true)
	Optional<User> findByEmail(String email);
	
	// SELECT * FROM user WHERE username = ?1 AND password = ?2
	//User findByIdUsernameAndPassword(String username, String password); //함수이름에 맞게 쿼리를 만들어냄
	
	//@Query(value = "select * from user",nativeQuery = true)
	//User find마음대로();
}
