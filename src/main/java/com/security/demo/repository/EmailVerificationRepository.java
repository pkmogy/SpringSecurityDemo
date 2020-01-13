package com.security.demo.repository;

import com.security.demo.entity.Someone;
import com.security.demo.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 李羅
 */
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

	/**
	 * @param verificationCode
	 * @return 
	 */
	EmailVerification findByVerificationCode(String verificationCode);

	/**
	 * @param talent 會員
	 * @return 
	 */
	EmailVerification findBySomeone(Someone talent);
}
