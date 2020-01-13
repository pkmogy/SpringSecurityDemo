package com.security.demo.service;

import com.security.demo.dto.RegistrationDto;
import com.security.demo.entity.Someone;
import com.security.demo.entity.EmailVerification;
import java.util.UUID;
import javax.transaction.Transactional;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.security.demo.repository.SomeoneRepository;
import com.security.demo.repository.EmailVerificationRepository;

/**
 *
 * @author 李羅
 */
@Service
public class TalentServiceImpl implements UserService {

	@Autowired
	private SomeoneRepository talentRepository;

	@Autowired
	private EmailVerificationRepository verificationTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 *
	 * @param accountDto 註冊Dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@Override
	public Someone registerNewUserAccount(RegistrationDto accountDto) throws Exception {

		if (emailExist(accountDto.getEmail())) {
			throw new Exception("There is an account with that email adress: " + accountDto.getEmail());
		}
		// the rest of the registration operation
		Someone talent = new Someone();
		talent.setNickname(accountDto.getNickName());
		talent.setShadow(passwordEncoder.encode(accountDto.getPassword()));
		talent.setEmail(accountDto.getEmail());
		talent.setRole("ROLE_ADMIN");

		return talentRepository.saveAndFlush(talent);
	}

	/**
	 * @param email 檢查Email是否已存在
	 * @return
	 */
	private boolean emailExist(String email) {
		Someone talent = talentRepository.findByEmail(email);
		if (talent != null) {
			return true;
		}
		return false;
	}

	/**
	 * @param verificationToken 取得驗證Token
	 * @return 會員資料
	 */
	@Override
	public Someone getUser(String verificationToken) {
		Someone talent = verificationTokenRepository.findByVerificationCode(verificationToken).getSomeone();
		return talent;
	}

	@Override
	public Someone getEmail(String email) {
		Someone talent = talentRepository.findByEmail(email);
		return talent;
	}

	/**
	 * @param VerificationToken 取得驗證Token
	 * @return 驗證Token
	 */
	@Override
	public EmailVerification getVerificationToken(String VerificationToken) {
		return verificationTokenRepository.findByVerificationCode(VerificationToken);
	}

	/**
	 * @param talent 儲存會員資料
	 */
	@Override
	public void saveRegisteredUser(Someone talent) {
		talentRepository.saveAndFlush(talent);
	}

	/**
	 * @param user 會員
	 * @param token 驗證Token
	 */
	@Override
	public void createVerificationToken(Someone user, String token) {
		EmailVerification myToken = new EmailVerification(token, user);
		verificationTokenRepository.save(myToken);
	}

	/**
	 * @param token 更新Token
	 * @return
	 */
	@Override
	public EmailVerification resetVerificationToken(String token) {
		EmailVerification myToken = verificationTokenRepository.findByVerificationCode(token);
		String newToken = UUID.randomUUID().toString();
		myToken.setVerificationCode(newToken);
		myToken.setExpiryDate();
		return verificationTokenRepository.save(myToken);
	}

	@Override
	public String resetShadow(Someone user) {
		String shadow=randomAlphanumeric(10);
		user.setShadow(passwordEncoder.encode(shadow));
		talentRepository.save(user);
		return shadow;
	}
}
