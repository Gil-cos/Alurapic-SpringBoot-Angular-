package br.com.alurapic.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.alurapic.api.model.User;

@Component
public interface UserService {

	User findByUserNameAndPassword(String userName, String password);

	User findByUserName(String userName);

	Long findByName(String userName);

	User findByEmail(String email);

	User createOrUpdate(User user);

	Optional<User> findById(Long id);

	void delete(Long id);

	Page<User> findAll(int page, int count);

	Boolean userExists(String userName);

}
