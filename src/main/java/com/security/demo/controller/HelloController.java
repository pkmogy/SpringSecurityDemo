package com.security.demo.controller;

import com.security.demo.entity.Someone;
import com.security.demo.entity.SystemMessage;
import com.security.demo.repository.SomeoneRepository;
import static com.security.demo.repository.SomeoneRepository.hasTag;
import com.security.demo.repository.SystemMessageRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	SomeoneRepository someoneRepository;

	@Secured({"ROLE_ADMIN"})
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

	@GetMapping("page")
	public ModelAndView page() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
		ModelAndView modelAndView = new ModelAndView("page");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@GetMapping("login.aspx")
	public ModelAndView login() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@GetMapping(path = "wtf.aspx", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String wtf(@RequestParam Integer id, HttpSession session) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		List<Integer> integers = (List<Integer>) session.getAttribute("wtf");
		if (Objects.isNull(integers)) {
			integers = new ArrayList<>();
		}

		if (!integers.contains(id)) {
			integers.add(id);
			session.setAttribute("wtf", integers);
		}

		for (Integer i : integers) {
			stringBuilder.append(i).append("\n");
		}

		System.err.println("The ID of this fucking session is:\t" + session.getId() + "!!!");
		return stringBuilder.toString();
	}

	@GetMapping(path = "criteria", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String criteria(@RequestParam(defaultValue = "") String name) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> tag = Arrays.asList(name.split("\\s"));
		
//		for (Someone someone : someoneRepository.findAll(hasTag(tag))) {
//			stringBuilder.
//				append(someone.getNickname()).
//				append(":").
//				append(someone.getEmail()).
//				append("\n");
//		}
		for (Someone someone : someoneRepository.findSomeoneByNickname(name)) {
			stringBuilder.
				append(someone.getNickname()).
				append(":").
				append(someone.getEmail()).
				append("\n");
		}
		return stringBuilder.toString();
	}
}
