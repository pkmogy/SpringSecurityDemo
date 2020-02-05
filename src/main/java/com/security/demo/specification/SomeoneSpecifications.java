package com.security.demo.specification;

import com.security.demo.entity.Someone;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author 李羅
 */
public final class SomeoneSpecifications {

	private SomeoneSpecifications() {
	}

	/**
	 * @param nicknames 模糊查詢-會員名稱
	 * @param tages 查詢-會員名稱
	 * @return
	 */
	public static Specification<Someone> likeNickname(Set<String> nicknames, Set<String> tages) {
		return new Specification<Someone>() {

			@SuppressWarnings("FieldNameHidesFieldInSuperclass")
			private static final long serialVersionUID = 6644524130903161741L;

			@Override
			public Predicate toPredicate(Root<Someone> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Set<Predicate> set = new HashSet<>();
				for (String nickname : nicknames) {
					set.add(criteriaBuilder.like(root.get("nickname"), "%" + nickname + "%"));
				}
				if (!tages.isEmpty()) {
					set.add(root.get("nickname").in(tages));
				}
				Predicate[] predicates = new Predicate[set.size()];
				predicates = set.toArray(predicates);
				return criteriaBuilder.or(predicates);
			}
		};
	}
}
