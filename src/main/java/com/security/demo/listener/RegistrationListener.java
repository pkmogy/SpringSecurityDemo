package com.security.demo.listener;

import com.security.demo.entity.Talent;
import com.security.demo.event.OnRegistrationCompleteEvent;
import com.security.demo.service.IUserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 註冊監聽器
 *
 * @author 李羅
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		Talent talent = event.getTalent();
		UUID token = UUID.randomUUID();
		iUserService.createVerificationToken(talent, token);

		String recipientAddress = talent.getEmail();
		String subject = "註冊確認信";
		String confirmationUrl = event.getAppUrl() + "/registration/confirm?token=" + token.toString();
		//String message = messageSource.getMessage("message.regSucc", null, event.getLocale());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("來認證喔: " + "http://localhost:8080" + confirmationUrl);
		javaMailSender.send(email);
	}
}
