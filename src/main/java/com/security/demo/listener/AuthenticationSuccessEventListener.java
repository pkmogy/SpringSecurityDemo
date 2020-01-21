package com.security.demo.listener;

import com.security.demo.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author 李羅
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private LoginAttemptService loginAttemptService;

	public void onApplicationEvent(AuthenticationSuccessEvent e) {
		WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
		
		// 回傳登入的位址
		loginAttemptService.loginSucceeded(auth.getRemoteAddress());
	}
}
