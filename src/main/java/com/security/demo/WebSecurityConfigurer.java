package com.security.demo;

import com.security.demo.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
	private MyUserDetailsService userDetailsService;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("powerfish0813@gmail.com").password(passwordEncoder().encode("123")).roles("ADMIN");
		auth.userDetailsService(userDetailsService);

	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.
			authorizeRequests().
			antMatchers("/registration/").permitAll(). // login.css 允許所有人請求
			antMatchers("/registration/confirm").permitAll().
			anyRequest().authenticated(). // 其它全部的路徑都得經過使用者驗證後才可以存取
			and().
			formLogin(). // 使用 Form Login 登入驗證
			permitAll(). // 允許所有人請求
			//.loginProcessingUrl("/login") // 對應自定義登入頁面的 action URI
			defaultSuccessUrl("/"). // 登入成功後導向的URI
			and().
			logout().
			// 如開啟CSRF功能, 會將 logout 預設為 POST, 在此設定使用任何 HTTP 方法請求(不建議)
			//logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
			logoutUrl("/logout"). // 登出的 URL
			logoutSuccessUrl("/login"). // 登出後的跳轉地址(預設值原為 /login?logout)
			permitAll().
			invalidateHttpSession(true). // 登出時是否 invalidate HttpSession
			deleteCookies("JSESSIONID"). // 登出同時清除 cookies
			and().
			csrf().
			// 預設開啟 CSRF 功能, 需設定 csrfTokenRepository() 以存取 CsrfToken 進行驗證
			//csrfTokenRepository(new HttpSessionCsrfTokenRepository());
			disable(); // 關閉 CSRF 防護
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
