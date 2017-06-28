package com.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lesson.models.CourseContent;

public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {
	CourseContent findByCourseContentId(Long courseContentId);

	@Transactional
	@Query(value = "delete from t_course_content  where course_content_id = :courseContentId",nativeQuery = true)
	@Modifying
	void deleteByCourseContentId( @Param("courseContentId") Long courseContentId);
}
