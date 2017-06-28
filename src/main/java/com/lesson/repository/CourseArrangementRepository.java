package com.lesson.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lesson.models.CourseArrangement;

public interface CourseArrangementRepository extends JpaRepository<CourseArrangement, Long> {
	
	@Query("select arrange from CourseArrangement arrange where arrange.courseId = :courseId order by arrange.courseArrangementId asc" )
    Page<CourseArrangement> findArrangementListByCourseId( @Param("courseId") Long courseId, Pageable pageable);
	
	
	@Transactional
	@Query(value = "delete from t_course_arrangement  where course_arrangement_id = :courseArrangementId",nativeQuery = true)
	@Modifying
	void deleteByCourseArrangementId( @Param("courseArrangementId") Long courseArrangementId);
	
	@Transactional
	@Query(value = "update t_course_arrangement set img_src = :imgSrc where course_arrangement_id = :courseArrangementId",nativeQuery = true)
	@Modifying
	void updateImgByCourseArrangementId(@Param("imgSrc") String imgSrc, @Param("courseArrangementId") Long courseArrangementId);
	
	
	@Transactional
	@Query(value = "update t_course_arrangement set name = :name where course_arrangement_id = :courseArrangementId",nativeQuery = true)
	@Modifying
	void updateNameByCourseArrangementId(@Param("name") String name, @Param("courseArrangementId") Long courseArrangementId);

}
