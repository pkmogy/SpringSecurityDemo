package com.security.demo.controller;

import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Talent;
import com.security.demo.service.TalentService;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 *
 * @author 李羅
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	TalentService talentService;

	private Talent createUserAccount(RegistrationDto registrationDto, BindingResult result) {
		Talent registered = null;
		try {
			registered = talentService.registerNewUserAccount(registrationDto);
		} catch (Exception e) {
			return null;
		}
		return registered;
	}

	public RegistrationDto testModeelAttribute(@RequestParam("nickName") String nickName, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("matchingPassword") String matchingPassword) {
		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setNickName(nickName);
		registrationDto.setEmail(email);
		registrationDto.setPassword(password);
		registrationDto.setPassword(matchingPassword);
		return registrationDto;
	}

	@GetMapping("/")
	public ModelAndView index() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
		ModelAndView modelAndView = new ModelAndView("registration");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@PostMapping("/")
	public ModelAndView registerUserAccount(@ModelAttribute("testModeelAttribute") @Valid RegistrationDto registrationDto, BindingResult result, WebRequest request, Errors errors) throws Exception {
		Talent talent = new Talent();
		if (!result.hasErrors()) {
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
		} else {
			return index();
		}
	}
}
