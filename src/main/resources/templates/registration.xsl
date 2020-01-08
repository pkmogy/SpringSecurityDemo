<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output
		encoding="UTF-8"
		media-type="text/html"
		method="html"
		indent="no"
		omit-xml-declaration="yes"
	/>

	<xsl:template match="/">
		<xsl:text disable-output-escaping="yes">&#60;!DOCTYPE HTML&#62;</xsl:text>
		<HTML lang="zh-TW">
			<HEAD>
				<TITLE>會員註冊</TITLE>
			</HEAD>
			<BODY>
				<xsl:apply-templates select="document"/>
			</BODY>
		</HTML>
	</xsl:template>

	<xsl:template match="document">
		<FORM action="/">
			<DIV class="form-group">
				<LABEL for="nickName">姓名</LABEL>
				<INPUT type="text" class="form-control" name="nickName" id="nickName" placeholder="nickName"/>
			</DIV>
			<DIV class="form-group">
				<LABEL for="email">信箱</LABEL>
				<INPUT type="email" class="form-control" name="email" id="email" aria-describedby="emailHelp" placeholder="Enter email"/>
			</DIV>
			<DIV class="form-group">
				<LABEL for="password">密碼</LABEL>
				<INPUT type="password" class="form-control" id="password" placeholder="Password" name="password"/>
			</DIV>
			<BUTTON type="submit" class="btn btn-primary">Submit</BUTTON>
		</FORM>
	</xsl:template>
</xsl:stylesheet>