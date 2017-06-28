package com.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lesson.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findByUserIdAndCourseContentId(Long userId, Long courseContentId);

}
