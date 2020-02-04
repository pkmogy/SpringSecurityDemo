package com.security.demo.repository;

import com.security.demo.entity.Someone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 李羅
 */
@Repository
public class SomeRepositoryImpl implements SomeoneCustomRepository {

	EntityManager entityManager;

	// constructor
	/**
	 *
	 * @param nickname
	 * @return
	 */
	@Override
	public List<Someone> findSomeoneByNickname(String nickname) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Someone> criteriaQuery = criteriaBuilder.createQuery(Someone.class);

		Root<Someone> someone = criteriaQuery.from(Someone.class);
		List<Predicate> predicates = new ArrayList<>();
		if (nickname != null) {
			System.out.print(nickname);
			List<String> tag = Arrays.asList(nickname.split("\\s"));
			predicates.add(someone.get("nickname").in(tag));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();

	}

//	@Override
//	public List<Someone> searchByKeywords(List<String> keywords) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//	}
}
