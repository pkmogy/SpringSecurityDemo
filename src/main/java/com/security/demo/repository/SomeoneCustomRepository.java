package com.security.demo.repository;

import com.security.demo.entity.Someone;
import java.util.List;

/**
 *
 * @author 李羅
 */
interface SomeoneCustomRepository {

	List<Someone> findSomeoneByNickname(String nickname);

//	List<Someone> searchByKeywords(List<String> keywords);
}
