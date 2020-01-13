package com.security.demo.repository;

import com.security.demo.entity.Someone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 李羅
 */
public interface SomeoneRepository extends JpaRepository<Someone, Long> {
	public Someone findByEmail(String email);
}
