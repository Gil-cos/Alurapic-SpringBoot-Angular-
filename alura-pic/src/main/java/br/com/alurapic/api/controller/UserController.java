package br.com.alurapic.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alurapic.api.enums.Profile;
import br.com.alurapic.api.model.User;
import br.com.alurapic.api.response.Response;
import br.com.alurapic.api.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping()
	public ResponseEntity<Response<User>> signup(HttpServletRequest request, @RequestBody User user, BindingResult result) {
	
		Response<User> response = new Response<User>();
		
		try {

			validateCreateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setProfile(Profile.ROLE_CUSTOMER);
			
			User userPersisted = userService.createOrUpdate(user);
			response.setData(userPersisted);

		} catch (DuplicateKeyException dE) {
			response.getErrors().add("User name already registered !");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}

	private void validateCreateUser(User user, BindingResult result) {
		if (user.getUserName() == null) {
			result.addError(new ObjectError("User", "Username no information"));
			return;
		}
	}

	@GetMapping(value = "/exists/{userName}")
	public Boolean userExists(@PathVariable String userName) {
		return userService.userExists(userName);
	}
}
