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
public class UserServiceImpl implements UserService {

	@Autowired
	private SomeoneRepository someoneRepository;

	@Autowired
	private EmailVerificationRepository emailVerificationRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 *
	 * @param registrationDto 註冊Dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@Override
	public Someone registerNewUserAccount(RegistrationDto registrationDto) throws Exception {
		if (emailExist(registrationDto.getEmail())) {
			throw new Exception("There is an account with that email adress: " + registrationDto.getEmail());
		}

		// the rest of the registration operation
		Someone someone = new Someone();
		someone.setNickname(registrationDto.getNickName());
		someone.setShadow(passwordEncoder.encode(registrationDto.getPassword()));
		someone.setEmail(registrationDto.getEmail());
		someone.setRole("ROLE_ADMIN");

		return someoneRepository.saveAndFlush(someone);
	}

	/**
	 * @param email 檢查Email是否已存在
	 * @return
	 */
	private boolean emailExist(String email) {
		Someone talent = someoneRepository.findByEmail(email);
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
		Someone talent = emailVerificationRepository.findByVerificationCode(verificationToken).getSomeone();
		return talent;
	}

	@Override
	public Someone getEmail(String email) {
		Someone talent = someoneRepository.findByEmail(email);
		return talent;
	}

	/**
	 * @param VerificationToken 取得驗證Token
	 * @return 驗證Token
	 */
	@Override
	public EmailVerification getVerificationToken(String VerificationToken) {
		return emailVerificationRepository.findByVerificationCode(VerificationToken);
	}

	/**
	 * @param talent 儲存會員資料
	 */
	@Override
	public void saveRegisteredUser(Someone talent) {
		someoneRepository.saveAndFlush(talent);
	}

	/**
	 * @param user 會員
	 * @param token 驗證Token
	 */
	@Override
	public void createVerificationToken(Someone user, String token) {
		EmailVerification myToken = new EmailVerification(token, user);
		emailVerificationRepository.save(myToken);
	}

	/**
	 * @param token 更新Token
	 * @return
	 */
	@Override
	public EmailVerification resetVerificationToken(String token) {
		EmailVerification myToken = emailVerificationRepository.findByVerificationCode(token);
		String newToken = UUID.randomUUID().toString();
		myToken.setVerificationCode(newToken);
		myToken.setExpiryDate();
		return emailVerificationRepository.save(myToken);
	}

	@Override
	public String resetShadow(Someone user) {
		String shadow=randomAlphanumeric(10);
		user.setShadow(passwordEncoder.encode(shadow));
		someoneRepository.save(user);
		return shadow;
	}
}
