package com.security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

/**
 *
 * @author 李羅
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
		resourceHandlerRegistry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
		defaultServletHandlerConfigurer.enable();
	}

	@Bean
	public ViewResolver getResolver() {
		XsltViewResolver xsltViewResolver = new XsltViewResolver();
		xsltViewResolver.setIndent(false);
		xsltViewResolver.setPrefix("classpath:/templates/");
		xsltViewResolver.setSuffix(".xsl");
//		xsltViewResolver.setUriResolver(new URIResolver());

		return xsltViewResolver;
	}
}
