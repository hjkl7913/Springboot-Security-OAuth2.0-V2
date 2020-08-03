# 스프링 시큐리티 기본 V1

### MySQL DB 및 사용자 생성

```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database security;
use security;
```

### SecurityConfig.java 권한 설정 방법

```java
// protected void configure(HttpSecurity http) throws Exception 함수내부에 권한 설정법
//access = 권한을 물어봄 access 함수를 쓰는 이유는 or , and 사용이 가능하다.
.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")

```

### 컨트롤러의 함수에 직접 권한 설정 하는 방법

```java
//특정 주소 접근시 (컨트롤러 접근전에) 권한 및 인증을 미리체크 SecurityConfig.java 에서 설정
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled = true)


// 컨트롤러에 어노테이션 거는법
@PostAuthorize("hasRole('ROLE_MANAGER')")
@PreAuthorize("hasRole('ROLE_MANAGER')")
@Secured("ROLE_MANAGER")

```
