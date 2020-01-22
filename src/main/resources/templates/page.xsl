<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:th="http://www.thymeleaf.org">
	<xsl:output
		encoding="UTF-8"
		media-type="text/html"
		method="html"
		indent="no"
		omit-xml-declaration="yes"
	/>

	<xsl:template match="/">
		<HTML lang="zh-TW" >
			<HEAD>
				<TITLE>會員登入</TITLE>
				<SCRIPT src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></SCRIPT>
				<SCRIPT src="/page.js"></SCRIPT>
			</HEAD>
			<BODY>
				<xsl:apply-templates select="document"/>
			</BODY>
		</HTML>
	</xsl:template>

	<xsl:template match="document">
		<H1 th:text="#{greeting}"></H1>
		<SPAN th:text="#{lang.change}"></SPAN>
		<SELECT id="locales">
			<OPTION value=""></OPTION>
			<OPTION value="ch" th:text="#{lang.ch}"></OPTION>
			<OPTION value="en" th:text="#{lang.en}"></OPTION>
		</SELECT>
	</xsl:template>
</xsl:stylesheet>