package com.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lesson.models.Course;
import com.lesson.repository.custom.CourseRepositoryCustom;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {
	Course findByCourseId(Long courseId);
	
}
