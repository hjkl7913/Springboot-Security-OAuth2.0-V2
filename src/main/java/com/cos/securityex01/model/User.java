package com.cos.securityex01.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM - Object Relation Mapping 자바오브젝트로 DB 테이블을 메핑

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id //프라이머리키 만들어줌
	@GeneratedValue(strategy = GenerationType.IDENTITY) //무조건 IDENTITY 넘버링 오라클sql 로 따지면 시퀀스
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN
	@CreationTimestamp
	private Timestamp createDate;

}
