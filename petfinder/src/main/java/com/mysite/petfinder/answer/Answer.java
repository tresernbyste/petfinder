package com.mysite.petfinder.answer;

import java.time.LocalDateTime;

import com.mysite.petfinder.find.Find;
import com.mysite.petfinder.user.RegistUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@ManyToOne
	private Find find;
	
	@ManyToOne
	private RegistUser author;
	
	private LocalDateTime modifyDate;

}
