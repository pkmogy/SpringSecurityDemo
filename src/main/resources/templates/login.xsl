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
		<form action="/signin/facebook" method="POST">
			<input type="hidden" name="scope" value="public_profile" />
			<input type="submit" value="Login using Facebook"/>
		</form>
	</xsl:template>
</xsl:stylesheet>