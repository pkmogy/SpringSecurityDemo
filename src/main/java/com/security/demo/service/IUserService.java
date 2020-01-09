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

	/*
	建立註冊DTO實作
	*/
	Talent registerNewUserAccount(RegistrationDto registrationDto) throws Exception;
	
	/*
	取得使用者實作
	*/
	Talent getUser(UUID verificationToken);
 
	void saveRegisteredUser(Talent user);
 
	void createVerificationToken(Talent user, UUID token);
 
	VerificationToken getVerificationToken(UUID VerificationToken);
}
