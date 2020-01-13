package com.security.demo.controller;

import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Someone;
import com.security.demo.entity.EmailVerification;
import com.security.demo.event.OnRegistrationCompleteEvent;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import com.security.demo.service.UserService;
import com.security.demo.repository.SomeoneRepository;

/**
 *
 * @author 李羅
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	SomeoneRepository talentRepository;

	@Autowired
	private UserService iUserService;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 建立會員
	 *
	 * @param registrationDto 註冊DTO
	 * @param result 驗證是否正確
	 * @return
	 */
	private Someone createUserAccount(RegistrationDto registrationDto, BindingResult result) {
		Someone registered = null;
		try {
			registered = iUserService.registerNewUserAccount(registrationDto);
		} catch (Exception e) {
			return null;
		}
		return registered;
	}

	/**
	 * 取得註冊DTO
	 *
	 * @param nickName 暱稱
	 * @param email 信箱
	 * @param password 密碼
	 * @param matchingPassword 再輸入密碼
	 * @return
	 */
	public RegistrationDto testModeelAttribute(@RequestParam("nickName") String nickName, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("matchingPassword") String matchingPassword) {
		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setNickName(nickName);
		registrationDto.setEmail(email);
		registrationDto.setPassword(password);
		registrationDto.setPassword(matchingPassword);
		return registrationDto;
	}

	/**
	 * @return 註冊頁面
	 * @throws Exception
	 */
	@GetMapping("/")
	public ModelAndView index() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
		ModelAndView modelAndView = new ModelAndView("registration");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 註冊會員
	 *
	 * @param registrationDto 會員資料驗證
	 * @param result 驗證是否正確
	 * @param request
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/")
	public ModelAndView registerUserAccount(@ModelAttribute("testModeelAttribute") @Valid RegistrationDto registrationDto, BindingResult result, WebRequest request, Errors errors) throws Exception {
		Someone talent = new Someone();
		if (!result.hasErrors()) {
			/*
			建立會員
			 */
			talent = createUserAccount(registrationDto, result);
		}
		if (talent == null) {
			result.rejectValue("email", "message.regError");
		}
		if (result.hasErrors()) {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
			ModelAndView modelAndView = new ModelAndView("registrations");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		try {
			String appUrl = "/registration/confirm";
			applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(talent, request.getLocale(), appUrl));
		} catch (Exception me) {
			System.out.print(me);
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
			ModelAndView modelAndView = new ModelAndView("registrations111");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return index();
	}

	@GetMapping("/confirm")
	public ModelAndView confirmRegistration(WebRequest request, @RequestParam("token") String token) throws Exception {

		Locale locale = request.getLocale();

		EmailVerification verificationToken = iUserService.getVerificationToken(token);
		if (verificationToken == null) {
			return new ModelAndView("redirect:/");
		}

		Someone talent = verificationToken.getSomeone();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiry().getTime() - cal.getTime().getTime()) <= 0) {
			return new ModelAndView("redirect:/registration/resendToken?token=" + token);
		}

		talent.setVerified(true);
		iUserService.saveRegisteredUser(talent);
		return index();
	}

	@GetMapping("/resendToken")
	public ModelAndView resendRegistration(WebRequest request, @RequestParam("token") String token) throws Exception {

		EmailVerification verificationToken = iUserService.resetVerificationToken(token);

		try {
			String appUrl = request.getContextPath();
			applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(verificationToken.getSomeone(), request.getLocale(), appUrl));
		} catch (Exception me) {
			return index();
		}
		return new ModelAndView("redirect:/");
	}
}
