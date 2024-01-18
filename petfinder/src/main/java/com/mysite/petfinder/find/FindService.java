package com.mysite.petfinder.find;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.petfinder.DataNotFoundException;
import com.mysite.petfinder.answer.Answer;
import com.mysite.petfinder.user.RegistUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FindService {
	private final FindRepository findRepository;

	public Page<Find> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		
		
		Specification<Find> spec = search(kw);
		return this.findRepository.findAll(spec, pageable);

	}

	public Find getFind(Integer id) {
		Optional<Find> find = this.findRepository.findById(id);
		if (find.isPresent()) {
			return find.get();
		} else {
			throw new DataNotFoundException("find not found");
		}
	}

	public void create(String subject, String content, RegistUser registUser) {
		Find f = new Find();
		f.setSubject(subject);
		f.setContent(content);
		f.setCreateDate(LocalDateTime.now());
		f.setAuthor(registUser);
		this.findRepository.save(f);
	}

	public void modify(Find find, String subject, String content) {
		find.setSubject(subject);
		find.setContent(content);
		find.setModifyDate(LocalDateTime.now());

		this.findRepository.save(find);

	}

	public void delete(Find find) {
		this.findRepository.delete(find);
	}

	private Specification<Find> search(String kw) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Find> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중 복 을 제 거
				Join<Find, RegistUser> u1 = q.join("author", JoinType.LEFT);
				Join<Find, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, RegistUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제 목
						cb.like(q.get("content"), "%" + kw + "%"), // 내 용
						cb.like(u1.get("username"), "%" + kw + "%"), // 질 문 작 성자
						cb.like(a.get("content"), "%" + kw + "%"), // 답 변 내 용
						cb.like(u2.get("username"), "%" + kw + "%")); // 답 변 작 성자
			}
		};
	}
}
