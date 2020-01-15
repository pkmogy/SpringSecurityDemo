package com.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author 李羅
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 *
	 * @param authenticationManagerBuilder
	 * @throws Exception
	 */
	@Override
	protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.inMemoryAuthentication().withUser("powerfish0813@gmail.com").password(passwordEncoder().encode("123")).roles("ADMIN");
		authenticationManagerBuilder.inMemoryAuthentication().withUser("rationality0@gmail.com").password(passwordEncoder().encode("123")).roles("USER");
		authenticationManagerBuilder.inMemoryAuthentication().withUser("ppp123@gmail.com").password(passwordEncoder().encode("123")).roles("SOMEONE");
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.
			authorizeRequests().
			antMatchers("/registration/", "/registration/confirm", "/registration/resendToken", "/talent/forgetPassword").permitAll(). // 允許所有人請求
			//antMatchers().hasAuthority("CHANGE_PASSWORD_PRIVILEGE").
			anyRequest().hasAnyRole("ADMIN", "USER"). // 其它全部的路徑都得經過使用者驗證後才可以存取
			and().
			formLogin(). // 使用 Form Login 登入驗證
			loginPage("/login.aspx"). // 自定義登入頁面
			permitAll(). // 允許所有人請求
			//.loginProcessingUrl("/login") // 對應自定義登入頁面的 action URI
			//defaultSuccessUrl("/"). // 登入成功後導向的URI
			and().
			logout().
			// 如開啟CSRF功能, 會將 logout 預設為 POST, 在此設定使用任何 HTTP 方法請求(不建議)
			//logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
			logoutUrl("/logout.aspx"). // 登出的 URL
			//logoutSuccessUrl("/login"). // 登出後的跳轉地址(預設值原為 /login?logout)
			permitAll().
			invalidateHttpSession(true). // 登出時是否 invalidate HttpSession
			deleteCookies("JSESSIONID"). // 登出同時清除 cookies
			and().
			rememberMe()
				.rememberMeParameter("remember") //from的忘記我欄位name
				.key("uniqueAndSecret")
				.tokenValiditySeconds(86400).//設定有效時間 ，預設是兩周，這裡設置為1天
			and().
			csrf().
			// 預設開啟 CSRF 功能, 需設定 csrfTokenRepository() 以存取 CsrfToken 進行驗證
			//csrfTokenRepository(new HttpSessionCsrfTokenRepository());
			disable(); // 關閉 CSRF 防護
	}

	@Bean("authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
