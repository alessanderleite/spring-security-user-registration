package br.com.alessanderleite.springsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.alessanderleite.springsecurity.model.User;
import br.com.alessanderleite.springsecurity.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {
	
	User findByEmail(String email);
	User sava(UserRegistrationDto registrationDto);
}
