package com.security.demo.controller;

import com.security.demo.entity.Someone;
import com.security.demo.event.OnResetShadowEvent;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import com.security.demo.service.UserService;

/**
 *
 * @author 李羅
 */
@Controller
@RequestMapping("/talent")
public class TalentController {

	@Autowired
	private UserService iUserService;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	/**
	 * @return 忘記密碼頁面
	 * @throws Exception
	 */
	@GetMapping("/forgetPassword")
	public ModelAndView index() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
		ModelAndView modelAndView = new ModelAndView("forgetPassword");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@PostMapping("/forgetPassword")
	public ModelAndView resetPassword(HttpServletRequest request, @RequestParam("email") String email) throws Exception {
		Someone talent = iUserService.getEmail(email);
		if (talent == null) {
			return index();
		}
		try {
			applicationEventPublisher.publishEvent(new OnResetShadowEvent(talent));
			System.out.print("iUserService");
		} catch (Exception me) {
			return index();
		}
		return new ModelAndView("redirect:/iUserService");
	}
}
