package com.cos.securityex01.test;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@RestController
public class OptionalControllerTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test/user/{id}")
	public User 옵셔널_유저찾기(@PathVariable int id) {
		
		// 방법 1
		Optional<User> userOptional = userRepository.findById(id); // findById 리턴 타입 Optional
		User user;
		
		if(userOptional.isPresent()) {
			user = userOptional.get();
		}else {
			user = new User();
		}
		
		return user;
		
//		return userRepository.findById(id).get();  //사용 x 오류생김
		
		
		//방법2
		// orElseGet  --> null이면 get() 빈객체를만들어서 을 리 턴 (내가 만들어서 리턴해도 됨)
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//		
//			@Override
//			public User get() {
//				
//				return new User();
//			}
//			
//		});
		
		
//		
//		User user = userRepository.findById(id).orElseGet(()-> {
//				return User.builder().id(5).username("아무개").email("1234@naver.com").build();
//		});
//		
//		return user;
		
		
		//방법3
		
//		User user = userRepository.findById(id).orElseThrow(new Supplier<NullPointerException>() {
//
//			@Override
//			public NullPointerException get() {
//				
//				return new NullPointerException("값 없어");
//			}
//			
//		});
//		
//		return user;
		
//		
//		User user = userRepository.findById(id).orElseThrow(()-> {
//
//				return new NullPointerException("값 없어");
//
//			
//		});
//		
//		return user;
	}

}
