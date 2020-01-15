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
				<TITLE>會員登入</TITLE>
			</HEAD>
			<BODY>
				<xsl:apply-templates select="document"/>
			</BODY>
		</HTML>
	</xsl:template>

	<xsl:template match="document">
		<FORM class="from" action="" method="POST">
			<DIV class="form-group">
				<LABEL for="username">帳號：</LABEL>
				<INPUT type="text" class="form-control" name="username" id="username" />
			</DIV>
			<DIV class="form-group">
				<LABEL for="password">密碼：</LABEL>
				<INPUT type="password" class="form-control" name="password" id="password" />
			</DIV>
			<DIV class="form-group">
				<INPUT type="checkbox" name="remember" id="remember"/>
				<LABEL for="remember">記住我</LABEL>
			</DIV>
			
			<BUTTON type="submit" class="btn btn-primary">送出</BUTTON>
		</FORM>
	</xsl:template>
</xsl:stylesheet>