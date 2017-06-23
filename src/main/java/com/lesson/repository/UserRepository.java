package com.lesson.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lesson.models.User;


@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	User findByPhone(String phone);
	User findByUserId(Long userId);
	
	@Query(value = "update t_user set note = :note where user_id = :userId",nativeQuery = true)
	@Modifying
	void updateNoteByUserId(@Param("note") String note, @Param("userId") Long userId);
	
	
	@Query(value = "update t_user set password = :password where user_id = :userId",nativeQuery = true)
	@Modifying
	void updatePasswordByUserId(@Param("password") String password, @Param("userId") Long userId);
	
	
	@Query("select u from User u where u.type = :type")
    Page<User> findUserListByType( @Param("type") Integer type, Pageable pageable);

}
