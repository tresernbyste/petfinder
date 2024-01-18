package com.mysite.petfinder.find;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.petfinder.answer.Answer;
import com.mysite.petfinder.user.RegistUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Find {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	
	@OneToMany(mappedBy = "find", cascade = CascadeType.REMOVE)
	private List<Answer> answerList; 
	
	@ManyToOne
	private RegistUser author;
	
	private LocalDateTime modifyDate;
}
