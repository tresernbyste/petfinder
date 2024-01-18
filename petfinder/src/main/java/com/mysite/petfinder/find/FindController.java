package com.mysite.petfinder.find;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.petfinder.answer.AnswerForm;
import com.mysite.petfinder.user.RegistUser;
import com.mysite.petfinder.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/find")
@RequiredArgsConstructor
@Controller
public class FindController {

	private final FindService findService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {

		Page<Find> paging = this.findService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);

		return "find_list";

	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Find find = this.findService.getFind(id);
		model.addAttribute("find", find);
		return "find_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String findCreate(FindForm findForm) {
		return "find_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String findCreate(@Valid FindForm findForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "find_form";
		}

		RegistUser registUser = this.userService.getUser(principal.getName());
		this.findService.create(findForm.getSubject(), findForm.getContent(), registUser);

		return "redirect:/find/list";

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String findModify(FindForm findForm, @PathVariable("id") Integer id, Principal principal) {
		Find find = this.findService.getFind(id);
		if (!find.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		findForm.setSubject(find.getSubject());
		findForm.setContent(find.getContent());
		return "find_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String findModify(@Valid FindForm findForm, BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "find_form";
		}
		Find find = this.findService.getFind(id);
		if (!find.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.findService.modify(find, findForm.getSubject(), findForm.getContent());
		return String.format("redirect:/find/detail/%s", id);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String findDelete(Principal principal, @PathVariable("id") Integer id) {
		Find find = this.findService.getFind(id);
		if (!find.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.findService.delete(find);
		return "redirect:/";
	}

}
