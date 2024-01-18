package com.mysite.petfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.petfinder.find.Find;
import com.mysite.petfinder.find.FindRepository;
import com.mysite.petfinder.find.FindService;

@SpringBootTest
class PetfinderApplicationTests {

	@Autowired
	private FindService findService;

	/*
	 * @Test void testJpa() { for (int i = 1; i <= 300; i++) { String subject =
	 * String.format("테 스 트 데 이 터 입 니 다:[%03d]", i); String content = "내 용 무";
	 * this.findService.create(subject, content);
	 * 
	 * }
	 */

//		Optional<Find> of = this.findRepository.findById(1);
//		if(of.isPresent()) {
//			Find f = of.get();
//			assertEquals("sbb가 무엇인가요?", f.getSubject());
//		}

	/*
	 * List<Find> all = this.findRepository.findAll(); assertEquals(2, all.size());
	 * 
	 * Find f = all.get(0); assertEquals("sbb가 무엇인가요?", f.getSubject());
	 */

}
