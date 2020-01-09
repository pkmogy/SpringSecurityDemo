package com.security.demo.service;

import com.security.demo.repository.TalentRepository;
import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Talent;
import com.security.demo.entity.VerificationToken;
import com.security.demo.repository.VerificationTokenRepository;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author 李羅
 */
@Service
public class TalentService implements IUserService {

	@Autowired
	private TalentRepository talentRepository;
	
	@Autowired
	private VerificationTokenRepository  verificationTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public Talent registerNewUserAccount(RegistrationDto accountDto) throws Exception {

		if (emailExist(accountDto.getEmail())) {
			throw new Exception("There is an account with that email adress: " + accountDto.getEmail());
		}
		// the rest of the registration operation
		Talent talent = new Talent();
		talent.setNickname(accountDto.getNickName());
		talent.setShadow(passwordEncoder.encode(accountDto.getPassword()));
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

	@Override
	public Talent getUser(UUID verificationToken) {
		Talent talent = verificationTokenRepository.findByToken(verificationToken).getTalent();
		return talent;
	}

	@Override
	public VerificationToken getVerificationToken(UUID VerificationToken) {
		return verificationTokenRepository.findByToken(VerificationToken);
	}

	@Override
	public void saveRegisteredUser(Talent talent) {
		talentRepository.saveAndFlush(talent);
	}

	@Override
	public void createVerificationToken(Talent user, UUID token) {
		VerificationToken myToken = new VerificationToken(token, user);
		verificationTokenRepository.save(myToken);
	}
}
