package com.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lesson.models.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
	Link findByLinkId(Long linkId);

}
