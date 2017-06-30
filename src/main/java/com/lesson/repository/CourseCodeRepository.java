package com.lesson.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.lesson.models.CourseCode;
import java.lang.String;


public interface CourseCodeRepository extends JpaRepository<CourseCode, Long> {
	CourseCode findByCourseCodeId(Long courseCodeId);
	CourseCode findByCourseCodeStr(String courseCodeStr);
	CourseCode findByUserIdAndCourseId(Long userId, Long courseId);
	
	Long countByUserId(Long userId);
	
	
	@Query("select code from CourseCode code where code.courseId = :courseId")
    Page<CourseCode> findCodeListByCourseId( @Param("courseId") Long courseId, Pageable pageable);

}
