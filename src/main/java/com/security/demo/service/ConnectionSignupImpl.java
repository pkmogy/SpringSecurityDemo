package com.security.demo.service;

import com.security.demo.entity.Someone;
import com.security.demo.repository.SomeoneRepository;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

/**
 *
 * @author 李羅
 */
@Service
public class ConnectionSignupImpl implements ConnectionSignUp {

	@Autowired
	private SomeoneRepository someoneRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String execute(Connection<?> connection) {
		Someone someone = new Someone();
		someone.setEmail(connection.fetchUserProfile().getEmail());
		someone.setShadow(passwordEncoder.encode(randomAlphanumeric(8)));
		someoneRepository.save(someone);
		return someone.getEmail();
	}
}
