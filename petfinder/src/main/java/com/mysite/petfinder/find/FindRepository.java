package com.mysite.petfinder.find;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindRepository extends JpaRepository<Find, Integer> {
	Find findBySubject(String subject);
	Find findBySubjectAndContent(String subject, String content);
	List<Find> findBySubjectLike(String subject);
	Page<Find> findAll(Pageable pageable);

	// 페이징
//	Page<Find> findAll(Pageable pageable);

	Page<Find> findAll(Specification<Find> spec, Pageable pageable);

}
