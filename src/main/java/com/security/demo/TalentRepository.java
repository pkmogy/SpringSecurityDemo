package com.security.demo;

import com.security.demo.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 李羅
 */
public interface TalentRepository extends JpaRepository<Talent, Long> {
	public Talent findByEmail(String email);
}
