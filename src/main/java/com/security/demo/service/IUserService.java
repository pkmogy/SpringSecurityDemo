/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security.demo.service;

import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Talent;
import com.security.demo.entity.VerificationToken;
import java.util.UUID;

/**
 *
 * @author 李羅
 */
public interface IUserService {

	/**
	 * 建立註冊DTO實作
	 * 
	 * @param registrationDto 註冊資料
	 * @return
	 * @throws Exception 
	 */
	Talent registerNewUserAccount(RegistrationDto registrationDto) throws Exception;
	
	/**
	 * 取得使用者實作
	 * 
	 * @param verificationToken 驗證Token
	 * @return 
	 */
	Talent getUser(UUID verificationToken);
 
	/**
	 * 儲存會員
	 * 
	 * @param user 會員 
	 */
	void saveRegisteredUser(Talent user);
 
	/*
	建立 token
	*/
	void createVerificationToken(Talent user, UUID token);
	
	/*
	更新 token
	*/
	VerificationToken resetVerificationToken(UUID token) throws Exception;
 
	/*
	取得 token
	*/
	VerificationToken getVerificationToken(UUID VerificationToken);
}
