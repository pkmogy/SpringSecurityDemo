package com.security.demo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author user
 */
@Controller
public class ErrorControllerImpl implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("classpath:/skeleton/index.xml");
		Element documentElement = document.getDocumentElement();

		Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String viewName = "error";

		if (errorStatusCode == null) {
			viewName.concat("unknown");
		} else {
			Integer statusCode = Integer.valueOf(errorStatusCode.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				viewName = statusCode.toString();
			}
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				viewName = statusCode.toString();
			}
		}

		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
