/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security.demo.repository;

import com.security.demo.entity.SystemMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;

/**
 *
 * @author 李羅
 */
public interface SystemMessageRepository extends JpaRepository<SystemMessage, Long> {

	@PostFilter("hasPermission(filterObject, 'READ')")
	List<SystemMessage> findAll();
}
