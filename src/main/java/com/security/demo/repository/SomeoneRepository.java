package com.security.demo.repository;

import com.security.demo.entity.Someone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 李羅
 */
@Repository
public interface SomeoneRepository extends JpaRepository<Someone, Long>, JpaSpecificationExecutor<Someone> {

	public Someone findByEmail(String email);
}
