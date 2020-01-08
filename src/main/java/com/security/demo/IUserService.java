/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security.demo;

import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Talent;

/**
 *
 * @author 李羅
 */
public interface IUserService {

	Talent registerNewUserAccount(RegistrationDto registrationDto) throws Exception;
}
