package br.com.alessanderleite.springsecurity.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alessanderleite.springsecurity.model.Role;
import br.com.alessanderleite.springsecurity.model.User;
import br.com.alessanderleite.springsecurity.repository.UserRepository;
import br.com.alessanderleite.springsecurity.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalide username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User();
		user.setFirstName(registrationDto.getFirstName());
		user.setLastName(registrationDto.getLastName());
		user.setEmail(registrationDto.getEmail());
		user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}
}
