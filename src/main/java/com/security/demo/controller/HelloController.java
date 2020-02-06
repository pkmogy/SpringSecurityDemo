package com.security.demo.controller;

import com.security.demo.entity.Someone;
import com.security.demo.entity.SystemMessage;
import com.security.demo.repository.SomeoneRepository;
import com.security.demo.repository.SystemMessageRepository;
import com.security.demo.specification.SomeoneSpecifications;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping(path = "criteria", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String criteria(@RequestParam(defaultValue = "") String queryString, HttpServletRequest request) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> vocabularies = Arrays.asList(queryString.split("\\s"));
		Set<String> names = new HashSet(), tags = new HashSet();
		for (String vocabulary : vocabularies) {
			if (vocabulary.matches("^[#].*$")) {
				tags.add(vocabulary.replaceAll("^[#]", ""));
			} else {
				names.add(vocabulary);
			}
		}
//		for (String name : names) {
//			System.err.println(name);
//		}
//		System.err.println("queryString=================================================" + queryString + "!");
//		for (String tag : tags) {
//			System.err.println(tag);
//		}
		for (Someone someone : someoneRepository.findAll(SomeoneSpecifications.likeNickname(names, tags))) {
			stringBuilder.
				append(someone.getNickname()).
				append(":").
				append(someone.getEmail()).
				append("\n");
		}
		return stringBuilder.toString();
	}
}
