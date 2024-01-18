package com.mysite.petfinder.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.petfinder.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public RegistUser create(String username, String email, String password) {
		RegistUser user = new RegistUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}
	
	public RegistUser getUser(String username) {
		Optional<RegistUser> registUser = this.userRepository.findByusername(username);
		
		if(registUser.isPresent()) {
			return registUser.get();
		}
		else {
			throw new DataNotFoundException("registuser not found");
		}
	}
}