package com.lesson.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lesson.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findByUserIdAndCourseContentId(Long userId, Long courseContentId);
	
	Long countByUserId(Long userId);
	
	Book findByBookId(Long bookId);
	
	@Query("select book from Book book where book.userId = :userId order by book.readDate desc")
    Page<Book> findBookListByUserId( @Param("userId") Long userId, Pageable pageable);

}
