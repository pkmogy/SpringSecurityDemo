package com.security.demo.controller;

import com.security.demo.entity.SystemMessage;
import com.security.demo.repository.SystemMessageRepository;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

/**
 *
 * @author 李羅
 */
@Controller
@RequestMapping("/")
public class HelloController {

	@Autowired
	SystemMessageRepository systemMessageRepository;

	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE, path = "")
	@ResponseBody
	String index() throws Exception {
		StringBuilder stringBuilder = new StringBuilder("message");
		for (SystemMessage systemMessage : systemMessageRepository.findAll()) {
			stringBuilder.append(":");
			stringBuilder.append(systemMessage.getContent());
		}
		return stringBuilder.toString();
	}

	@GetMapping("login.aspx")
	public ModelAndView login() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}
}
