package br.com.alurapic.api.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.alurapic.api.enums.Profile;
import br.com.alurapic.api.model.User;

public class JwtUserFactory {
	
	private JwtUserFactory() {
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getUserName(), user.getPassword(),
				mapToGrantedAuthorities(user.getProfile()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Profile profileEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
		return authorities;
	}
}