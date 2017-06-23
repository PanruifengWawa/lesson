package com.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lesson.models.Token;


public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByTokenStr(String tokenSrr);
	Token findByUserId(Long userId);
	
	@Transactional
	@Query(value = "delete from t_token  where user_id = :userId",nativeQuery = true)
	@Modifying
	void deleteByUserId( @Param("userId") Long userId);

}
