package br.com.alessanderleite.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alessanderleite.springsecurity.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
