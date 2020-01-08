package com.security.demo.controller;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

/**
 *
 * @author 李羅
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@GetMapping("/")
	public ModelAndView index() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");

		ModelAndView modelAndView = new ModelAndView("registration");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}
}
