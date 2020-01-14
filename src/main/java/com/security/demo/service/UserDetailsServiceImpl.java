package com.security.demo.service;

import com.security.demo.entity.Someone;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.security.demo.repository.SomeoneRepository;

/**
 *
 * @author 李羅
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SomeoneRepository someoneRepository;

	/**
	 *
	 * @param email
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Someone someone = someoneRepository.findByEmail(email);
		if (someone == null) {
			throw new UsernameNotFoundException("No user found with username: " + email);
		}
		boolean verfied = someone.isVerified();
//		boolean accountNonExpired = true;
//		boolean credentialsNonExpired = true;
//		boolean accountNonLocked = true;
		return new org.springframework.security.core.userdetails.User(
			someone.getEmail().toLowerCase(),
			someone.getShadow(),
			verfied,
			true, //accountNonExpired,
			true, //credentialsNonExpired,
			true, //accountNonLocked,
			getAuthorities(someone.getRole())
		);
	}

	/**
	 *
	 * @param role
	 * @return
	 */
	private static List<GrantedAuthority> getAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
}
