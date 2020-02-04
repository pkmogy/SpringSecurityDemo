package com.security.demo.repository;

import com.security.demo.entity.Someone;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 李羅
 */
@Repository
public interface SomeoneRepository extends JpaRepository<Someone, Long>, JpaSpecificationExecutor<Someone>, SomeoneCustomRepository {

	static Specification<Someone> hasNickname(List<String> nickname) {
		return (someone, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(someone.get("nickname"), "%李%");
	}

	static Specification<Someone> hasTag(List<String> tag) {
		return (someone, criteriaQuery, criteriaBuilder) -> someone.get("nickname").in(tag);
	}

	public Someone findByEmail(String email);
}
