package br.com.alurapic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alurapic.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserNameAndPassword(String userName, String password);

	User findByUserName(String userName);

	User findByEmail(String email);

	@Query("SELECT id FROM User u WHERE u.userName = :userName")
	Long findByName(String userName);
}
