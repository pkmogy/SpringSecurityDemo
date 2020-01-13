package com.security.demo.listener;

import com.security.demo.entity.Someone;
import com.security.demo.event.OnResetShadowEvent;
import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 *
 * @author 李羅
 */
@Component
public class ResetShadowListener implements ApplicationListener<OnResetShadowEvent> {
	@Autowired
	private UserService iUserService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void onApplicationEvent(OnResetShadowEvent event) {
		this.confirmRegistration(event);
	}
	
	private void confirmRegistration(OnResetShadowEvent event) {
		Someone talent = event.getTalent();
		String resetShadow=iUserService.resetShadow(talent);

		String recipientAddress = talent.getEmail();
		String subject = "忘記密碼確認信";
		//String message = messageSource.getMessage("message.regSucc", null, event.getLocale());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText("你的密碼為："+resetShadow+"，請立即登入修改密碼");
		javaMailSender.send(email);
	}
}
