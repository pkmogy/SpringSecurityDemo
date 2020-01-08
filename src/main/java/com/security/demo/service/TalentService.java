package com.security.demo.service;

import com.security.demo.IUserService;
import com.security.demo.TalentRepository;
import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Talent;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 李羅
 */
@Service
public class TalentService implements IUserService {

	@Autowired
	private TalentRepository talentRepository;

	@Transactional
	@Override
	public Talent registerNewUserAccount(RegistrationDto accountDto) throws Exception {

		if (emailExist(accountDto.getEmail())) {
			throw new Exception("There is an account with that email adress: " + accountDto.getEmail());
		}
		// the rest of the registration operation
		Talent talent = new Talent();
		talent.setNickname(accountDto.getNickName());
		talent.setShadow(accountDto.getPassword());
		talent.setEmail(accountDto.getEmail());
		talent.setRole("ROLE_ADMIN");

		return talentRepository.saveAndFlush(talent);
	}

	private boolean emailExist(String email) {
		Talent talent = talentRepository.findByEmail(email);
		if (talent != null) {
			return true;
		}
		return false;
	}
}
