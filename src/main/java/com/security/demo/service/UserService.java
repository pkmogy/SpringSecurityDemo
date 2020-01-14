/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security.demo.service;

import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Someone;
import com.security.demo.entity.EmailVerification;

/**
 *
 * @author 李羅
 */
public interface UserService {

	/**
	 * 建立註冊DTO實作
	 * 
	 * @param registrationDto 註冊資料
	 * @return
	 * @throws Exception 
	 */
	Someone registerNewUserAccount(RegistrationDto registrationDto) throws Exception;
	
	/**
	 * 取得使用者實作
	 * 
	 * @param verificationToken 驗證Token
	 * @return 
	 */
	Someone getUser(String verificationToken);
	
	/**
	 * 取得使用者實作
	 * 
	 * @param email 信箱
	 * @return 
	 */
	Someone getEmail(String email);
 
	/**
	 * 儲存會員
	 * 
	 * @param user 會員 
	 */
	void saveRegisteredUser(Someone user);
 
	/*
	建立 token
	*/
	void createVerificationToken(Someone user, String token);
	
	/*
	更新 token
	*/
	EmailVerification resetVerificationToken(String token) throws Exception;
 
	/*
	取得 token
	*/
	EmailVerification getVerificationToken(String VerificationToken);
	
	/*
	重置密碼
	*/
	String resetShadow(Someone user);
}
