package com.security.demo.repository;

import com.security.demo.entity.Talent;
import com.security.demo.entity.VerificationToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 李羅
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	/**
	 * @param token
	 * @return 
	 */
	VerificationToken findByToken(UUID token);

	/**
	 * @param talent 會員
	 * @return 
	 */
	VerificationToken findByTalent(Talent talent);
}
