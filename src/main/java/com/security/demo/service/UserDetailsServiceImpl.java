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
	private SomeoneRepository talentRepository;
	// 

	public UserDetails loadUserByUsername(String email)
		throws UsernameNotFoundException {

		Someone talent = talentRepository.findByEmail(email);
		if (talent == null) {
			throw new UsernameNotFoundException("No user found with username: " + email);
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		return new org.springframework.security.core.userdetails.User(talent.getEmail().toLowerCase(),
			talent.getShadow(), enabled, accountNonExpired,
			credentialsNonExpired, accountNonLocked,
			getAuthorities(talent.getRole()));
	}

	private static List<GrantedAuthority> getAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
}
